package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    public static final String SQL_CREATE_TAG= "INSERT INTO tag(name) VALUES (?)";

    private static final String TABLE_NAME = "tag";
    private static final String NAME_COLUMN_LABEL = "name";
    private static final String ID_COLUMN_LABEL = "id";
    private static final String TAG_ID = "tag_id";
    private static final RowMapper<Tag> ROW_MAPPER = new BeanPropertyRowMapper<>(Tag.class);
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        super(ROW_MAPPER, TABLE_NAME, jdbcTemplate, TAG_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Tag tag) {
        jdbcTemplate.update(SQL_CREATE_TAG, tag.getName());
    }

    public Optional<Tag> findByName(String name) {
        return findByColumn(NAME_COLUMN_LABEL, name);
    }


}