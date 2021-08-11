package com.epam.esm.repository.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RoleRepositoryImpl extends AbstractRepository<Role> implements RoleRepository {

    @Autowired
    public RoleRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Role.class);
    }

    @Override
    public Role findByName(String name) {
        return null;
    }
}
