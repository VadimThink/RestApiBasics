package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.query.SortingParameters;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftCertificateRepository extends CrudRepository<GiftCertificate> {

    List<GiftCertificate> getAllWithSortingFiltering(SortingParameters sortParameters, List<String> tagNames, String partInfo/*, Pageable pageable*/);
    //todo
}
