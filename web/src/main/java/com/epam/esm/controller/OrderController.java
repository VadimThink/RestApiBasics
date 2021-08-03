package com.epam.esm.controller;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.logic.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public OrderDto createOrder(@RequestBody @Valid CreateOrderDto createOrderDto,
                                BindingResult bindingResult) throws NoSuchEntityException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        OrderDto orderDto = orderService.create(createOrderDto);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }

}
