package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order> {

    List<Order> getAllByUserId(long userId, int page, int size);
    List<Order> getAllByUserId(long userId);
}
