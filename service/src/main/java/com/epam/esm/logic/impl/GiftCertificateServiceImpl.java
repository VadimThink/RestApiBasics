package com.epam.esm.logic.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.UpdateGiftCertificateMapper;
import com.epam.esm.query.SortingParameters;
import com.epam.esm.query.SortingParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateMapper mapper;
    private final UpdateGiftCertificateMapper updateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, GiftCertificateMapper mapper,
                                      UpdateGiftCertificateMapper updateMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.mapper = mapper;
        this.updateMapper = updateMapper;
    }

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateException, NoSuchEntityException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        String certificateName = giftCertificate.getName();
        boolean isCertificateExist = giftCertificateDao.findByName(certificateName).isPresent();
        if (isCertificateExist) {
            throw new DuplicateException("certificate.exist");
        }
        giftCertificateDao.create(giftCertificate);
        GiftCertificate newGiftCertificate = giftCertificateDao.findByName(certificateName)
                .orElseThrow(() -> new NoSuchEntityException("certificate.not.created"));
        if (giftCertificate.getTagList() != null) {
            createCertificateTagsWithReference(giftCertificate.getTagList(), newGiftCertificate.getId());
        }
        addTagsInGiftCertificate(newGiftCertificate);
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
    @Transactional
    public GiftCertificateDto findById(long id) throws NoSuchEntityException {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(id);
        if (giftCertificateOptional.isEmpty()) {
            throw new NoSuchEntityException("certificate.not.found");
        } else {
            GiftCertificate giftCertificate = giftCertificateOptional.get();
            addTagsInGiftCertificate(giftCertificate);
            return mapper.mapToDto(giftCertificate);
        }
    }

    @Override
    @Transactional
    public GiftCertificateDto updateById(long id, UpdateGiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = updateMapper.mapToEntity(giftCertificateDto);
        if (giftCertificate != null) {
            if (giftCertificateDao.findById(id).isEmpty()) {
                throw new NoSuchEntityException("certificate.not.found");
            }
            giftCertificateDao.updateById(id, giftCertificate, false);
            List<Tag> tags = giftCertificate.getTagList();
            if (tags != null) {
                updateCertificateTags(tags, id);
            }
        }
        GiftCertificate newGiftCertificate = giftCertificateDao.findById(id).orElse(new GiftCertificate());
        addTagsInGiftCertificate(newGiftCertificate);
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
    public GiftCertificateDto replaceById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        if (giftCertificate != null) {
            if (giftCertificateDao.findById(id).isEmpty()) {
                throw new NoSuchEntityException("certificate.not.found");
            }
            giftCertificateDao.updateById(id, giftCertificate, true);
            List<Tag> tags = giftCertificate.getTagList();
            if (tags != null) {
                replaceCertificateTags(tags, id);
            }
        }
        GiftCertificate newGiftCertificate = giftCertificateDao.findById(id).orElse(new GiftCertificate());
        addTagsInGiftCertificate(newGiftCertificate);
        return mapper.mapToDto(newGiftCertificate);
    }

    private void replaceCertificateTags(List<Tag> tags, long certificateId) {
        List<Long> addedTagIds = new ArrayList<>();
        for (Tag tag : tags) {
            String tagName = tag.getName();
            Optional<Tag> tagOptional = tagDao.findByName(tagName);
            Tag fullTag = tagOptional.orElseGet(() -> createCertificateTag(tag));
            long tagId = fullTag.getId();
            addedTagIds.add(tagId);
            if (!giftCertificateDao.getTagIdsByCertificateId(certificateId).contains(tagId)) {
                giftCertificateDao.createCertificateTagReference(certificateId, tagId);
            }
        }
        List<Long> allCertificateTagIdsList = giftCertificateDao.getTagIdsByCertificateId(certificateId);
        for (long curTagId : allCertificateTagIdsList) {
            if (!addedTagIds.contains(curTagId)) {
                tagDao.deleteById(curTagId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) throws NoSuchEntityException {
        Optional<GiftCertificate> certificateOptional = giftCertificateDao.findById(id);
        if (certificateOptional.isEmpty()) {
            throw new NoSuchEntityException("certificate.not.found");
        }
        giftCertificateDao.deleteById(id);
    }

    @Override
    public List<GiftCertificateDto> findBySearchParams
            (String tagName, String partName,
             List<String> sortColumns, List<String> orderTypes) throws NoSuchEntityException {
        List<GiftCertificateDto> giftCertificateDtoList = new ArrayList<>();
        List<GiftCertificate> giftCertificates;
        boolean isSortExist = sortColumns != null;
        if (isSortExist) {
            SortingParameters sortParameters = new SortingParameters(sortColumns, orderTypes);
            SortingParametersValidator.validateParams(sortParameters);
            if (isFilterExist(tagName, partName)) {
                giftCertificates = findCertificatesWithSortingAndFiltering(tagName, partName, sortParameters);
            } else {
                giftCertificates = giftCertificateDao.getWithSorting(sortParameters);
            }
        } else if (isFilterExist(tagName, partName)) {
            giftCertificates = findCertificatesWithFiltering(tagName, partName);
        } else {
            giftCertificates = giftCertificateDao.getAll();
        }
        for (GiftCertificate giftCertificate : giftCertificates) {
            addTagsInGiftCertificate(giftCertificate);
            giftCertificateDtoList.add(mapper.mapToDto(giftCertificate));
        }
        return giftCertificateDtoList;
    }

    private void addTagsInGiftCertificate(GiftCertificate giftCertificate) {
        List<Tag> tagList = new ArrayList<>();
        List<Long> tagIds = giftCertificateDao.getTagIdsByCertificateId(giftCertificate.getId());
        for (long tagId : tagIds) {
            Optional<Tag> tagOptional = tagDao.findById(tagId);
            tagOptional.ifPresent(tagList::add);
        }
        giftCertificate.setTagList(tagList);
    }

    private boolean isFilterExist(String tagName, String partInfo) {
        return tagName != null || partInfo != null;
    }

    private List<GiftCertificate> findCertificatesWithSortingAndFiltering
            (String tagName, String partInfo, SortingParameters sortParameters) throws NoSuchEntityException {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.getWithSortingAndFiltering(sortParameters, certificateIdsByTagName, partInfo);
    }

    private List<GiftCertificate> findCertificatesWithFiltering(String tagName, String partName) throws NoSuchEntityException {
        List<Long> certificateIdsByTagName = null;
        if (tagName != null) {
            certificateIdsByTagName = findCertificateIdsByTagName(tagName);
        }
        return giftCertificateDao.getWithFiltering(certificateIdsByTagName, partName);
    }

    private List<Long> findCertificateIdsByTagName(String tagName) throws NoSuchEntityException {
        Optional<Tag> tag = tagDao.findByName(tagName);
        if (tag.isEmpty()) {
            throw new NoSuchEntityException("tag.not.found");
        }
        long tagId = tag.get().getId();
        return giftCertificateDao.getCertificateIdsByTagId(tagId);
    }
}
