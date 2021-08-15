package com.epam.esm.repository;

import com.epam.esm.entity.Role;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}
