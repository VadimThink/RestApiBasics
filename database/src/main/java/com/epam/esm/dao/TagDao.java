package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {

    void create(Tag tag);
    Optional<Tag> findById(long id);
    Optional<Tag> findByName(String name);
    List<Tag> getAll();
    void deleteById(long id);
}
