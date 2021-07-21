package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order>{

    List<Order> getAllByUserId(long userId/*, Pageable pageable*/);

}
