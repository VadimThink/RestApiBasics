package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Order.class);
    }

    @Override
    public List<Order> getAllByUserId(long userId, int page, int size) {
        CriteriaQuery<Order> query = buildGetAllQuery(userId);

        return entityManager.createQuery(query)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<Order> getAllByUserId(long userId){
        CriteriaQuery<Order> query = buildGetAllQuery(userId);

        return entityManager.createQuery(query)
                .getResultList();
    }

    private CriteriaQuery<Order> buildGetAllQuery(long userId){
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);

        Join<User, Order> userJoin = root.join("user");
        Predicate joinIdPredicate = builder.equal(userJoin.get("id"), userId);
        query.where(joinIdPredicate);

        return query;
    }
}
