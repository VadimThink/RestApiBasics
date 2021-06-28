package com.epam.esm.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

/**
 * The type Abstract dao.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractDao<T> {
    protected final String findByIdQuery;
    protected final String findByColumnQuery;
    protected final String deleteByIdQuery;
    protected final String deleteReference;
    protected final String getAllQuery;

    private final RowMapper<T> rowMapper;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Instantiates a new Abstract dao.
     *
     * @param rowMapper           the row mapper
     * @param tableName           the table name
     * @param jdbcTemplate        the jdbc template
     * @param referenceColumnName the reference column name
     */
    public AbstractDao(RowMapper<T> rowMapper, String tableName, JdbcTemplate jdbcTemplate, String referenceColumnName) {
        this.rowMapper = rowMapper;
        this.jdbcTemplate = jdbcTemplate;

        getAllQuery = "SELECT * FROM " + tableName;
        findByIdQuery = "SELECT * FROM " + tableName + " WHERE id=?";
        findByColumnQuery = "SELECT * FROM " + tableName + " WHERE %s=?";
        deleteByIdQuery = "DELETE FROM " + tableName + " WHERE id=?";
        deleteReference = "DELETE FROM certificate_tags WHERE " + referenceColumnName + "=?";
    }

    public Optional<T> findById(long id) {
        return jdbcTemplate.query(findByIdQuery, rowMapper, id).stream().findAny();
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    public void deleteById(long id) {
        jdbcTemplate.update(deleteReference, id);
        jdbcTemplate.update(deleteByIdQuery, id);
    }

    /**
     * Find by column optional.
     *
     * @param columnName the column name
     * @param value      the value
     * @return the optional
     */
    public Optional<T> findByColumn(String columnName, String value) {
        String query = String.format(findByColumnQuery, columnName);
        return jdbcTemplate.query(query, rowMapper, value).stream().findAny();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<T> getAll() {
        return jdbcTemplate.query(getAllQuery, rowMapper);
    }
}
