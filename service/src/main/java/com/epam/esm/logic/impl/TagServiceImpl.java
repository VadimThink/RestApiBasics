package com.epam.esm.logic.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.TagService;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          UserRepository userRepository, OrderRepository orderRepository,
                          TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public TagDto create(TagDto tagDto) throws DuplicateException {
        String tagName = tagDto.getName();
        boolean isTagExist = tagRepository.findByName(tagName).isPresent();
        if (isTagExist) {
            throw new DuplicateException("tag.exist");
        }
        Tag tag = tagMapper.mapToEntity(tagDto);
        return tagMapper.mapToDto(tagRepository.create(tag));
    }

    @Override
    public List<TagDto> getAll(int page, int size) {
        return tagMapper.mapListToDto(tagRepository.getAll(page, size));
    }

    @Override
    @Transactional
    public TagDto getById(long id) throws NoSuchEntityException {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("tag.not.found"));
        return tagMapper.mapToDto(tag);
    }

    @Override
    @Transactional
    public void deleteById(long id) throws NoSuchEntityException {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (!optionalTag.isPresent()) {
            throw new NoSuchEntityException("tag.not.found");
        }
        tagRepository.deleteById(id);
    }

    @Override
    public TagDto findTheMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrders() throws NoSuchEntityException {
        User user = userRepository.findUserWithMaxSpentMoney();
        long theMostWidelyUsedTadId = user.getOrders().stream()
                .map(Order::getGiftCertificate)
                .map(GiftCertificate::getTagList)
                .flatMap(List<Tag>::stream)
                .collect(Collectors.groupingBy(Tag::getId, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NoSuchEntityException("tag.list.is.empty"));
        return tagMapper.mapToDto(tagRepository.findById(theMostWidelyUsedTadId)
                .orElseThrow(()->new NoSuchEntityException("tag.not.found")));
    }

    /*public static final String MOST_WIDELY_USED_TAG_QUERY =
            "SELECT t.id as 'id', t.name as 'name'\n" +
                    "FROM orders o\n" +
                    "JOIN certificates_tags ct on o.certificate_id = ct.certificate_id\n" +
                    "JOIN tags t on ct.tag_id = t.id\n" +
                    "WHERE o.user_id = :userId\n" +
                    "GROUP BY t.id\n" +
                    "ORDER BY COUNT(t.id) DESC\n" +
                    "LIMIT 1";*/

}
