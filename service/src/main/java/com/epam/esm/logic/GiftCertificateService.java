package com.epam.esm.logic;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UpdateGiftCertificateDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {

    /**
     * Create gift certificate dto.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     * @throws DuplicateException    the duplicate exception
     * @throws NoSuchEntityException the no such entity exception
     */
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto) throws DuplicateException, NoSuchEntityException;

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws NoSuchEntityException the no such entity exception
     */
    GiftCertificateDto findById(long id) throws NoSuchEntityException;

    /**
     * Update by id gift certificate dto.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     * @throws NoSuchEntityException the no such entity exception
     */
    GiftCertificateDto updateById(long id, UpdateGiftCertificateDto giftCertificateDto) throws NoSuchEntityException;

    /**
     * Replace by id gift certificate dto.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     * @throws NoSuchEntityException the no such entity exception
     */
    GiftCertificateDto replaceById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws NoSuchEntityException the no such entity exception
     */
    void deleteById(long id) throws NoSuchEntityException;

    /**
     * Gets by search params.
     *
     * @param tagName     the tag name
     * @param partName    the part name
     * @param sortColumns the sort columns
     * @param orderTypes  the order types
     * @return the by search params
     * @throws NoSuchEntityException the no such entity exception
     */
    List<GiftCertificateDto> findBySearchParams(String tagName, String partName,
                                                List<String> sortColumns, List<String> orderTypes) throws NoSuchEntityException;
}
