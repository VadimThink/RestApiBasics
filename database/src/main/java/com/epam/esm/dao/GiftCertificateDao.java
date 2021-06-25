package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.query.SortingParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GiftCertificateDao {

    void create(GiftCertificate giftCertificate);

    void updateById(long id, GiftCertificate giftCertificate);

    Optional<GiftCertificate> findById(long id);

    Optional<GiftCertificate> findByName(String name);

    void deleteById(long id);

    void createCertificateTagReference(long certificateId, long tagId);

    List<GiftCertificate> getAll();

    List<GiftCertificate> getWithSorting(SortingParameters sortingParameters);

    List<GiftCertificate> getWithFiltering(List<Long> certificateIdsByTagName, String partName);

    List<GiftCertificate> getWithSortingAndFiltering(SortingParameters sortingParameters,
                                                     List<Long> certificateIdsByTagName, String partName);

    List<Long> getTagIdsByCertificateId(long certificateId);

    List<Long> getCertificateIdsByTagId(long tagId);


}
