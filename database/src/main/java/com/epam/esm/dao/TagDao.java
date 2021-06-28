package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao {

    /**
     * Create.
     *
     * @param tag the tag
     */
    void create(Tag tag);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Tag> findById(long id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<Tag> getAll();

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(long id);
}
