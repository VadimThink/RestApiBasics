package com.epam.esm.logic.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.TagService;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository,
                          UserRepository userRepository, TagMapper tagMapper) {
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
    public List<TagDto> getAll(int page, int size) throws InvalidParametersException {
        /*Pageable pageRequest;
        try {
            pageRequest = PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new InvalidParametersException("invalid.pagination");
        }todo*/

        return tagMapper.mapListToDto(tagRepository.getAll(/*pageRequest*/));
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
}
