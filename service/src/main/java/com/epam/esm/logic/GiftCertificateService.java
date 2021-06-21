package com.epam.esm.logic;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

public interface GiftCertificateService {

    long create(GiftCertificateDto giftCertificateDto) throws DuplicateException;
    List<GiftCertificateDto> getAll();
    GiftCertificateDto getById(long id) throws NoSuchEntityException;
    GiftCertificateDto updateById(long id, GiftCertificateDto giftCertificateDto) throws NoSuchEntityException;
    void deleteById(long id);
    List<GiftCertificateDto> getBySearchParams(String tagName, String partName,
                                               List<String> sortColumns, List<String> orderTypes);
}
