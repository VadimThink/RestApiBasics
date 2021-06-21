package com.epam.esm.logic.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public long create(GiftCertificateDto giftCertificateDto) throws DuplicateException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        String certificateName = giftCertificate.getName();
        boolean isCertificateExist = giftCertificateDao.findByName(certificateName).isPresent();
        if (isCertificateExist) {
            throw new DuplicateException("Certificate already exist");
        } else {
            giftCertificateDao.create(giftCertificate);
            long certificateId = giftCertificateDao.findByName(certificateName)
                    .map(GiftCertificate::getId).orElse(-1L);
            createCertificateTagsWithReference(giftCertificate.getTagList(), certificateId);
            return certificateId;
        }
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
        return tagDao.findByName(tag.getName()).get();
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
    public GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateDto);
        if(giftCertificate != null){
            if(giftCertificateDao.findById(id).isEmpty()){
                throw new NoSuchEntityException("Certificate not found");
            }
            else {
                giftCertificateDao.updateById(id, findUpdateInfo(giftCertificate));
            }
        }
        List<Tag> tags = giftCertificate.getTagList();
        if (tags != null) {
            updateCertificateTags(tags, id);
        }
        GiftCertificate newGiftCertificate = giftCertificateDao.findById(id).get();
        return mapper.mapToDto(giftCertificate);
    }

    private Map<String, Object> findUpdateInfo(GiftCertificate giftCertificate) {
        Map<String, Object> updateInfo = new HashMap<>();
        String name = giftCertificate.getName();
        if (name != null) {
            updateInfo.put("name", name);
        }
        String description = giftCertificate.getDescription();
        if (description != null) {
            updateInfo.put("description", description);
        }
        BigDecimal price = giftCertificate.getPrice();
        if (price != null) {
            updateInfo.put("price", price);
        }
        int duration = giftCertificate.getDuration();
        if (duration != 0) {
            updateInfo.put("duration", duration);
        }
        return updateInfo;
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
    public void deleteById(long id) {

    }

    @Override
    public List<GiftCertificateDto> getBySearchParams(String tagName, String partName, List<String> sortColumns, List<String> orderTypes) {
        return null;
    }
}
