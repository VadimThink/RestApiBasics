package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends AbstractEntity> {

    T create(T entity);

    List<T> getAll();

    Optional<T> findById(long id);

    Optional<T> findByField(String columnName, Object value);

    T update(T entity);

    void deleteById(long id);
}
