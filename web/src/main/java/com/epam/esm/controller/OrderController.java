package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderLinkProvider orderLinkProvider;

    @Autowired
    public OrderController(OrderService orderService, OrderLinkProvider orderLinkProvider) {
        this.orderService = orderService;
        this.orderLinkProvider = orderLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestParam(name = "user_id") long userId,
                                @RequestParam(name = "certificate_id") long certificateId) throws NoSuchEntityException {
        //todo validation
        OrderDto orderDto = orderService.create(userId, certificateId);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }

}
