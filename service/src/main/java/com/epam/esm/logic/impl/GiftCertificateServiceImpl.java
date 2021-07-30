package com.epam.esm.logic.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.UpdateGiftCertificateMapper;
import com.epam.esm.query.SortingParameters;
import com.epam.esm.query.SortingParametersValidator;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository certificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateMapper giftCertificateMapper,
                                      UpdateGiftCertificateMapper updateGiftCertificateMapper) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateMapper = giftCertificateMapper;
        this.updateGiftCertificateMapper = updateGiftCertificateMapper;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        List<Tag> tagsToAdd = new ArrayList<>();
        if (giftCertificate.getTagList() != null) {
            for (Tag tag : giftCertificate.getTagList()) {
                Optional<Tag> tagOptional = tagRepository.findByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tagsToAdd.add(tagOptional.get());
                } else {
                    tagsToAdd.add(tag);
                }
            }
        }
        giftCertificate.setTagList(tagsToAdd);
        return giftCertificateMapper.mapToDto(certificateRepository.create(giftCertificate));
    }

    @Override
    @Transactional
    public GiftCertificateDto findById(long id) throws NoSuchEntityException {
        GiftCertificate giftCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("certificate.not.found"));
        return giftCertificateMapper.mapToDto(giftCertificate);
    }

    @Override
    @Transactional(rollbackFor = NoSuchEntityException.class)
    public GiftCertificateDto updateById(long id, UpdateGiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("certificate.not.found"));
        GiftCertificate updateGiftCertificate = updateGiftCertificateMapper.mapToEntity(giftCertificateDto);
        updateFields(giftCertificate, updateGiftCertificate);
        if (giftCertificate.getTagList() != null) {
            List<Tag> tags = giftCertificate.getTagList();
            giftCertificate.setTagList(addTags(tags));
        }
        return giftCertificateMapper.mapToDto(certificateRepository.update(giftCertificate));
    }

    @Override
    @Transactional(rollbackFor = NoSuchEntityException.class)
    public GiftCertificateDto replaceById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("certificate.not.found"));
        GiftCertificate replaceGiftCertificate = giftCertificateMapper.mapToEntity(giftCertificateDto);
        updateFields(giftCertificate, replaceGiftCertificate);
        if (giftCertificate.getTagList() != null) {
            List<Tag> tags = giftCertificate.getTagList();
            giftCertificate.setTagList(addTags(tags));
        }
        return giftCertificateMapper.mapToDto(certificateRepository.update(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(long id) throws NoSuchEntityException {
        certificateRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("certificate.not.found"));
        certificateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findBySearchParams(List<String> tagNames, String partName,
                                                       List<String> sortColumns, List<String> orderTypes,
                                                       int page, int size) {
        SortingParameters sortParameters = null;
        if (sortColumns != null) {
            sortParameters = new SortingParameters(sortColumns, orderTypes);
            SortingParametersValidator.validateParams(sortParameters);
        }
        return giftCertificateMapper.mapListToDto(
                certificateRepository.getAllWithSortingFiltering(
                        sortParameters, tagNames, partName, page, size));
    }

    private List<Tag> addTags(List<Tag> tags) {
        List<Tag> addedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
            Tag savedTag = optionalTag.orElseGet(() -> tagRepository.create(tag));
            addedTags.add(savedTag);
        }
        return addedTags;
    }

    private void updateFields(GiftCertificate giftCertificate, GiftCertificate updateGiftCertificate) {
        String name = updateGiftCertificate.getName();
        if (name != null && !giftCertificate.getName().equals(name)) {
            giftCertificate.setName(name);
        }
        String description = updateGiftCertificate.getDescription();
        if (description != null && !giftCertificate.getDescription().equals(description)) {
            giftCertificate.setDescription(description);
        }
        BigDecimal price = updateGiftCertificate.getPrice();
        if (price != null && giftCertificate.getPrice().compareTo(price) != 0) {
            giftCertificate.setPrice(price);
        }
        int duration = updateGiftCertificate.getDuration();
        if (duration != 0 && giftCertificate.getDuration() != duration) {
            giftCertificate.setDuration(duration);
        }
    }

}
