package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.query.SortingParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao {

    /**
     * Create.
     *
     * @param giftCertificate the gift certificate
     */
    void create(GiftCertificate giftCertificate);

    /**
     * Update by id.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     * @param isReplace       the is replace
     */
    void updateById(long id, GiftCertificate giftCertificate, boolean isReplace);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<GiftCertificate> findById(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(long id);

    /**
     * Create certificate tag reference.
     *
     * @param certificateId the certificate id
     * @param tagId         the tag id
     */
    void createCertificateTagReference(long certificateId, long tagId);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<GiftCertificate> getAll();

    /**
     * Gets with sorting.
     *
     * @param sortingParameters the sorting parameters
     * @return the with sorting
     */
    List<GiftCertificate> getWithSorting(SortingParameters sortingParameters);

    /**
     * Gets with filtering.
     *
     * @param certificateIdsByTagName the certificate ids by tag name
     * @param partName                the part name
     * @return the with filtering
     */
    List<GiftCertificate> getWithFiltering(List<Long> certificateIdsByTagName, String partName);

    /**
     * Gets with sorting and filtering.
     *
     * @param sortingParameters       the sorting parameters
     * @param certificateIdsByTagName the certificate ids by tag name
     * @param partName                the part name
     * @return the with sorting and filtering
     */
    List<GiftCertificate> getWithSortingAndFiltering(SortingParameters sortingParameters,
                                                     List<Long> certificateIdsByTagName, String partName);

    /**
     * Gets tag ids by certificate id.
     *
     * @param certificateId the certificate id
     * @return the tag ids by certificate id
     */
    List<Long> getTagIdsByCertificateId(long certificateId);

    /**
     * Gets certificate ids by tag id.
     *
     * @param tagId the tag id
     * @return the certificate ids by tag id
     */
    List<Long> getCertificateIdsByTagId(long tagId);


}
