package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractRepository<Tag> implements TagRepository {

    @Autowired
    protected TagRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Tag.class);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return findByField("name", name);
    }

}
