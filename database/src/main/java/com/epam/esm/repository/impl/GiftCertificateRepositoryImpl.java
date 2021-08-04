package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.query.SortingParameters;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl extends AbstractRepository<GiftCertificate>
        implements GiftCertificateRepository {

    @Autowired
    public GiftCertificateRepositoryImpl(EntityManager entityManager) {
        super(entityManager, GiftCertificate.class);
    }

    @Override
    public List<GiftCertificate> getAllWithSortingFiltering(SortingParameters sortParameters,
                                                            List<String> tagNames, String partInfo, int page, int size) {
        CriteriaQuery<GiftCertificate> query = builder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (tagNames != null) {
            predicates.add(buildPredicateByTagName(root, tagNames));
        }
        if (partInfo != null) {
            predicates.add(buildPredicateByPartInfo(root, partInfo));
        }
        if (!predicates.isEmpty()) {
            query.where(queryBuildHelper.buildAndPredicates(predicates));
            if (tagNames != null) {
                query.groupBy(root.get("id"));
                query.having(builder.greaterThanOrEqualTo(builder.count(root), (long) tagNames.size()));
            }
        }

        if (sortParameters != null) {
            List<Order> orderList = queryBuildHelper.buildOrderList(root, sortParameters);
            if (!orderList.isEmpty()) {
                query.orderBy(orderList);
            }
        }

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    private Predicate buildPredicateByTagName(Root<GiftCertificate> root, List<String> tagNames) {
        Join<GiftCertificate, Tag> tagsJoin = root.join("tagList");

        return queryBuildHelper.buildOrEqualPredicates(tagsJoin, "name", tagNames);
    }

    private Predicate buildPredicateByPartInfo(Root<GiftCertificate> root, String partInfo) {
        String regexValue = queryBuildHelper.convertToRegex(partInfo);
        Predicate predicateByNameInfo = builder.like(root.get("name"), regexValue);
        Predicate predicateByDescriptionInfo = builder.like(root.get("description"), regexValue);

        return builder.or(predicateByNameInfo, predicateByDescriptionInfo);
    }

}
