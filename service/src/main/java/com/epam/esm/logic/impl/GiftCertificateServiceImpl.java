package com.epam.esm.logic.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.query.SortingParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateMapper mapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, GiftCertificateMapper mapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateException, NoSuchEntityException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        String certificateName = giftCertificate.getName();
        boolean isCertificateExist = giftCertificateDao.findByName(certificateName).isPresent();
        if (isCertificateExist) {
            throw new DuplicateException("Certificate already exist");
        }
        giftCertificateDao.create(giftCertificate);
        GiftCertificate newGiftCertificate = giftCertificateDao.findByName(certificateName)
                .orElseThrow(() -> new NoSuchEntityException("Cant find this entity"));
        if(giftCertificate.getTagList() != null) {
            createCertificateTagsWithReference(giftCertificate.getTagList(), newGiftCertificate.getId());
        }
        GiftCertificateDto newGiftCertificateDto = mapper.mapToDto(newGiftCertificate);
        return newGiftCertificateDto;
    }

    private void createCertificateTagsWithReference(List<Tag> tags, long certificateId) {
        for (Tag tag : tags) {
            String tagName = tag.getName();
            Optional<Tag> tagOptional = tagDao.findByName(tagName);
            Tag fullTag = tagOptional.orElseGet(() -> createCertificateTag(tag));
            long tagId = fullTag.getId();
            giftCertificateDao.createCertificateTagReference(certificateId, tagId);
        }
    }

    private Tag createCertificateTag(Tag tag) {
        tagDao.create(tag);
        Tag result = tagDao.findByName(tag.getName()).orElse(new Tag());
        return result;
    }

    @Override
    public List<GiftCertificateDto> getAll() {
        return null;
    }

    @Override
    public GiftCertificateDto getById(long id) throws NoSuchEntityException {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (giftCertificateOptional.isEmpty()) {
            throw new NoSuchEntityException("Certificate not found");
        } else {
            return mapper.mapToDto(giftCertificateOptional.get());
        }
    }

    @Override
    @Transactional
    public GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        if (giftCertificate != null) {
            if (giftCertificateDao.findById(id).isEmpty()) {
                throw new NoSuchEntityException("Certificate not found");
            } else {
                giftCertificateDao.updateById(id, giftCertificate);
            }
        }
        List<Tag> tags = null;
        if (giftCertificate != null) {
            tags = giftCertificate.getTagList();
        }
        if (tags != null) {
            updateCertificateTags(tags, id);
        }

        GiftCertificate newGiftCertificate = giftCertificateDao.findById(id).orElse(new GiftCertificate());
        return mapper.mapToDto(newGiftCertificate);
    }

    private void updateCertificateTags(List<Tag> tags, long certificateId) {
        for (Tag tag : tags) {
            String tagName = tag.getName();
            Optional<Tag> tagOptional = tagDao.findByName(tagName);
            Tag fullTag = tagOptional.orElseGet(() -> createCertificateTag(tag));
            long tagId = fullTag.getId();
            if (!giftCertificateDao.getTagIdsByCertificateId(certificateId).contains(tagId)) {
                giftCertificateDao.createCertificateTagReference(certificateId, tagId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) throws NoSuchEntityException {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
        if (certificateOptional.isEmpty()) {
            throw new NoSuchEntityException("Certificate not found");
        }
        giftCertificateDao.deleteById(id);
    }

    @Override
    public List<GiftCertificateDto> getBySearchParams
            (String tagName, String partName,
             List<String> sortColumns, List<String> orderTypes) throws NoSuchEntityException {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        List<GiftCertificate> giftCertificates;
        boolean isSortExist = sortColumns != null;
        if (isSortExist) {
            SortingParameters sortParameters = new SortingParameters(sortColumns, orderTypes);
            if (isFilterExist(tagName, partName)) {
                giftCertificates = getCertificatesWithSortingAndFiltering(tagName, partName, sortParameters);
            } else {
                giftCertificates = giftCertificateDao.getWithSorting(sortParameters);
            }
        } else if (isFilterExist(tagName, partName)) {
            giftCertificates = getCertificatesWithFiltering(tagName, partName);
        } else {
            giftCertificates = giftCertificateDao.getAll();
        }
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificateDtoList.add(mapper.mapToDto(giftCertificate));
        }
        return giftCertificateDtoList;
    }

    private boolean isFilterExist(String tagName, String partInfo) {
        return tagName != null || partInfo != null;
    }

    private List<GiftCertificate> getCertificatesWithSortingAndFiltering
            (String tagName, String partInfo, SortingParameters sortParameters) throws NoSuchEntityException {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.getWithSortingAndFiltering(sortParameters, certificateIdsByTagName, partInfo);
    }

    private List<GiftCertificate> getCertificatesWithFiltering(String tagName, String partName) throws NoSuchEntityException {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.getWithFiltering(certificateIdsByTagName, partName);
    }

    private List<Long> findCertificateIdsByTagName(String tagName) throws NoSuchEntityException {
        Optional<Tag> tag = tagDao.findByName(tagName);
        if (tag.isEmpty()) {
            throw new NoSuchEntityException("Tag not found");
        }
        long tagId = tag.get().getId();
        return giftCertificateDao.getCertificateIdsByTagId(tagId);
    }
}
