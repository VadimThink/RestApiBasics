package com.epam.esm.logic;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

public interface OrderService {

    OrderDto create(CreateOrderDto createOrderDto) throws NoSuchEntityException;

    /**
     * Gets all Orders by user id.
     *
     * @param userId User id who has orders
     * @param page   page number of Orders
     * @param size   page size
     * @return founded Orders
     * @throws NoSuchEntityException      when User not found
     * @throws InvalidParametersException when page or size params are invalid
     */
    List<OrderDto> getAllByUserId(long userId, int page, int size) throws NoSuchEntityException, InvalidParametersException;

    /**
     * Gets Order by order and user id.
     *
     * @param userId  User id who has order
     * @param orderId Order id to search
     * @return founded Order
     * @throws NoSuchEntityException when User or Order not found
     */
    OrderDto getByUserId(long userId, long orderId) throws NoSuchEntityException;
}
