package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.epam.esm.query.QueryBuildHelper;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    private static final String SQL_CREATE_CERTIFICATE = "INSERT INTO gift_certificate" +
            "(name, description, price, duration) VALUES (?, ?, ?, ?)";
    private static final String SQL_CREATE_CERTIFICATE_TAG_REFERENCE = "INSERT INTO " +
            "certificates_tags(certificate_id, tag_id) VALUES (?, ?)";
    private static final String SQL_GET_TAG_IDS_BY_CERTIFICATE_ID = "SELECT * FROM " +
            "certificates_tags WHERE certificate_id=?";
    private static final String SQL_GET_CERTIFICATE_IDS_BY_TAG_ID = "SELECT * FROM " +
            "certificates_tags WHERE tag_id=?";

    private static final String TABLE_NAME = "gift_certificate";
    private static final RowMapper<GiftCertificate> ROW_MAPPER =
            (rs, rowNum) -> new GiftCertificate(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBigDecimal("price"),
                    rs.getInt("duration"),
                    rs.getDate("create_date").toLocalDate(),
                    rs.getDate("last_update_date").toLocalDate());

    private final JdbcTemplate jdbcTemplate;
    private final QueryBuildHelper queryBuildHelper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, QueryBuildHelper queryBuildHelper) {
        super(ROW_MAPPER, TABLE_NAME, jdbcTemplate);
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
    public List<Long> getTagIdsByCertificateId(long certificateId) {
        return jdbcTemplate.query(SQL_GET_TAG_IDS_BY_CERTIFICATE_ID,
                (resultSet, i) -> resultSet.getLong("tag_id"), certificateId);
    }

    @Override
    public List<Long> getCertificateIdsByTagId(long tagId) {
        return jdbcTemplate.query(SQL_GET_CERTIFICATE_IDS_BY_TAG_ID,
                (resultSet, i) -> resultSet.getLong("certificate_id"), tagId);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return findByColumn("name", name);
    }

    @Override
    public void updateById(long id, Map<String, Object> giftCertificateUpdateInfo) {
        if (!giftCertificateUpdateInfo.isEmpty()) {
            StringBuilder updateQueryBuilder = new StringBuilder();
            updateQueryBuilder.append("UPDATE gift_certificate SET last_update_date=NOW(), ");
            String updateColumnsQuery = queryBuildHelper.buildUpdateColumnsQuery(
                    giftCertificateUpdateInfo.keySet());
            updateQueryBuilder.append(updateColumnsQuery);
            updateQueryBuilder.append(" WHERE id=?");
            List<Object> values = new ArrayList<>(giftCertificateUpdateInfo.values());
            values.add(id);
            jdbcTemplate.update(updateQueryBuilder.toString(), values.toArray());
        }
    }
}
