package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.query.QueryBuildHelper;
import com.epam.esm.query.SortingParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.*;

import static com.epam.esm.dao.SqlRequest.*;
import static com.epam.esm.dao.constant.ColumnNames.*;

@Component
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private static final String TABLE_NAME = "gift_certificate";

    private static final RowMapper<GiftCertificate> ROW_MAPPER =
            (rs, rowNum) -> new GiftCertificate(
                    rs.getLong(ID_COLUMN_LABEL),
                    rs.getString(NAME_COLUMN_LABEL),
                    rs.getString(DESCRIPTION_COLUMN_LABEL),
                    rs.getBigDecimal(PRICE_COLUMN_LABEL),
                    rs.getInt(DURATION_COLUMN_LABEL),
                    rs.getTimestamp(6).toLocalDateTime().atZone(ZoneId.of("GMT+3")),
                    rs.getTimestamp(LAST_UPDATE_DATE_COLUMN_LABEL).toLocalDateTime().atZone(ZoneId.of("GMT+3")));

    private final JdbcTemplate jdbcTemplate;
    private final QueryBuildHelper queryBuildHelper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, QueryBuildHelper queryBuildHelper) {
        super(ROW_MAPPER, TABLE_NAME, jdbcTemplate, CERTIFICATE_ID);
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuildHelper = queryBuildHelper;
    }

    @Override
    public void create(GiftCertificate giftCertificate) {
        jdbcTemplate.update(SQL_CREATE_CERTIFICATE, giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(),
                giftCertificate.getDuration());
    }

    @Override
    public void createCertificateTagReference(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_CREATE_CERTIFICATE_TAG_REFERENCE, certificateId, tagId);
    }


    @Override
    public List<GiftCertificate> getWithSorting(SortingParameters sortingParameters) {
        String query = getAllQuery + " " + queryBuildHelper.buildSortingQuery(sortingParameters);
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    @Override
    public List<GiftCertificate> getWithFiltering(List<Long> certificateIdsByTagName, String partName) {
        List<Object> values = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        fillFilterQueryInfo(certificateIdsByTagName, partName, queryBuilder, values);
        if (queryBuilder.toString().isBlank()){
            return new ArrayList<>();
        }
        return jdbcTemplate.query(queryBuilder.toString(), ROW_MAPPER, values.toArray());
    }

    private void fillFilterQueryInfo(List<Long> certificateIdsByTagName, String partInfo,
                                     StringBuilder queryBuilder, List<Object> values) {
        boolean isIdsExist = certificateIdsByTagName != null && !certificateIdsByTagName.isEmpty();
        if (isIdsExist) {
            queryBuilder.append(getAllQuery).append(" WHERE ");
            String inFilter = queryBuildHelper.buildInFilteringQuery("id", certificateIdsByTagName.size());
            queryBuilder.append(inFilter);
            values.addAll(certificateIdsByTagName);
        }
        if (partInfo != null) {
            if (isIdsExist) {
                queryBuilder.append("AND ");
            }else {
                queryBuilder.append(getAllQuery).append(" WHERE ");
            }
            queryBuilder.append("(name LIKE ? OR description LIKE ?)");
            String regexPartInfo = queryBuildHelper.buildRegexValue(partInfo);
            values.add(regexPartInfo);
            values.add(regexPartInfo);
        }
    }

    @Override
    public List<GiftCertificate> getWithSortingAndFiltering(SortingParameters sortingParameters, List<Long> certificateIdsByTagName, String partName) {
        List<Object> values = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();
        //queryBuilder.append(getAllQuery).append(" WHERE ");
        fillFilterQueryInfo(certificateIdsByTagName, partName, queryBuilder, values);
        if (queryBuilder.toString().isBlank()){
            return new ArrayList<>();
        }
        queryBuilder.append(" ").append(queryBuildHelper.buildSortingQuery(sortingParameters));
        return jdbcTemplate.query(queryBuilder.toString(), ROW_MAPPER, values.toArray());
    }

    @Override
    public List<Long> getTagIdsByCertificateId(long certificateId) {
        return jdbcTemplate.query(SQL_GET_TAG_IDS_BY_CERTIFICATE_ID,
                (resultSet, i) -> resultSet.getLong(TAG_ID_COLUMN_LABEL), certificateId);
    }

    @Override
    public List<Long> getCertificateIdsByTagId(long tagId) {
        return jdbcTemplate.query(SQL_GET_CERTIFICATE_IDS_BY_TAG_ID,
                (resultSet, i) -> resultSet.getLong(CERTIFICATE_ID), tagId);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return findByColumn(NAME_COLUMN_LABEL, name);
    }

    @Override
    public void updateById(long id, GiftCertificate giftCertificate, boolean isReplace) {
        Map<String, Object> giftCertificateUpdateInfo;
        if (isReplace) {
            giftCertificateUpdateInfo = findReplaceInfo(giftCertificate);
        } else {
            giftCertificateUpdateInfo = findUpdateInfo(giftCertificate);
        }
        if (!giftCertificateUpdateInfo.isEmpty()) {
            StringBuilder updateQueryBuilder = new StringBuilder();
            updateQueryBuilder.append("UPDATE gift_certificate SET last_update_date= NOW()");
            updateQueryBuilder.append(",");
            String updateColumnsQuery = queryBuildHelper.buildUpdateColumnsQuery(
                    giftCertificateUpdateInfo.keySet());
            updateQueryBuilder.append(updateColumnsQuery);
            updateQueryBuilder.append(" WHERE id=?");
            List<Object> values = new ArrayList<>(giftCertificateUpdateInfo.values());
            values.add(id);
            jdbcTemplate.update(updateQueryBuilder.toString(), values.toArray());
        }
    }

    private Map<String, Object> findUpdateInfo(GiftCertificate giftCertificate) {
        Map<String, Object> updateInfo = new HashMap<>();
        String name = giftCertificate.getName();
        if (name != null) {
            updateInfo.put(NAME_COLUMN_LABEL, name);
        }
        String description = giftCertificate.getDescription();
        if (description != null) {
            updateInfo.put(DESCRIPTION_COLUMN_LABEL, description);
        }
        BigDecimal price = giftCertificate.getPrice();
        if (price != null) {
            updateInfo.put(PRICE_COLUMN_LABEL, price);
        }
        int duration = giftCertificate.getDuration();
        if (duration != 0) {
            updateInfo.put(DURATION_COLUMN_LABEL, duration);
        }
        return updateInfo;
    }

    private Map<String, Object> findReplaceInfo(GiftCertificate giftCertificate) {
        Map<String, Object> replaceInfo = new HashMap<>();
        String name = giftCertificate.getName();
        if (name != null) {
            replaceInfo.put(NAME_COLUMN_LABEL, name);
        }
        String description = giftCertificate.getDescription();
        replaceInfo.put(DESCRIPTION_COLUMN_LABEL, description);
        BigDecimal price = giftCertificate.getPrice();
        replaceInfo.put(PRICE_COLUMN_LABEL, price);
        int duration = giftCertificate.getDuration();
        replaceInfo.put(DURATION_COLUMN_LABEL, duration);
        return replaceInfo;
    }
}
