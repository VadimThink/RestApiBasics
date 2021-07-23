package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.OrderService;
import com.epam.esm.logic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        //todo validation
        return userService.create(userDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(@RequestParam(value = "page", required = true) int page,
                                @RequestParam(value = "size", required = true) int size) throws InvalidParametersException {
        return userService.getAll(page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable long id) throws NoSuchEntityException {
        return userService.getById(id);
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable long id,
                                            @RequestParam(value = "page", required = true) int page,
                                            @RequestParam(value = "size", required = true) int size)
            throws InvalidParametersException, NoSuchEntityException {
       return orderService.getAllByUserId(id, page, size);
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderByUserId(@PathVariable(value = "id") long id, @PathVariable(value = "orderId") long orderId)
            throws NoSuchEntityException {
        return orderService.getByUserId(id, orderId);
    }
}
