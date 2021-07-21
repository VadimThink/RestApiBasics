package com.epam.esm.repository;

import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.query.QueryBuildHelper;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends AbstractEntity> implements CrudRepository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;

    protected final CriteriaBuilder builder;
    protected final QueryBuildHelper queryBuildHelper;
    protected final Class<T> entityClass;

    protected AbstractRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.builder = entityManager.getCriteriaBuilder();
        this.queryBuildHelper = new QueryBuildHelper(this.builder);
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<T> getAll(/*Pageable pageable*/) {
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        query.select(root);

        return entityManager.createQuery(query)
                /*.setFirstResult((int)pageable.getOffset())
                .setMaxResults(pageable.getPageSize()) todo*/
                .getResultList();
    }

    @Override
    public Optional<T> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<T> findByField(String columnName, Object value) {
        CriteriaQuery<T> entityQuery = builder.createQuery(entityClass);
        Root<T> entityRoot = entityQuery.from(entityClass);
        entityQuery.select(entityRoot);

        Predicate fieldPredicate = builder.equal(entityRoot.get(columnName), value);
        entityQuery.where(fieldPredicate);

        TypedQuery<T> typedQuery = entityManager.createQuery(entityQuery);
        return queryBuildHelper.getOptionalQueryResult(typedQuery);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(long id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}
