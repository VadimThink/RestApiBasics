package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @Autowired
    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
