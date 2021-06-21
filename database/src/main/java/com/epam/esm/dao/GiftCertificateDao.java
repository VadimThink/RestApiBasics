package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    void create(GiftCertificate giftCertificate);

    void updateById(long id, Map<String, Object> giftCertificateUpdateInfo);

    Optional<GiftCertificate> findById(long id);

    Optional<GiftCertificate> findByName(String name);

    void deleteById(long id);

    void createCertificateTagReference(long certificateId, long tagId);

    List<GiftCertificate> getAll();

    List<Long> getTagIdsByCertificateId(long certificateId);

    List<Long> getCertificateIdsByTagId(long tagId);
}
