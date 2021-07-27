package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.link.UserLinkProvider;
import com.epam.esm.logic.OrderService;
import com.epam.esm.logic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.constant.RequestParams.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    private final UserLinkProvider userLinkProvider;
    private final OrderLinkProvider orderLinkProvider;

    @Autowired
    public UserController(UserService userService, OrderService orderService,
                          UserLinkProvider userLinkProvider, OrderLinkProvider orderLinkProvider) {
        this.userService = userService;
        this.orderService = orderService;
        this.userLinkProvider = userLinkProvider;
        this.orderLinkProvider = orderLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        //todo validation
        UserDto newUserDto = userService.create(userDto);
        userLinkProvider.provideLinks(newUserDto);
        return newUserDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(@RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                                @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size) throws InvalidParametersException {
        List<UserDto> userDtoList = userService.getAll(page, size);
        return userDtoList.stream()
                .peek(userLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@PathVariable long id) throws NoSuchEntityException {
        UserDto userDto = userService.getById(id);
        userLinkProvider.provideLinks(userDto);
        return userDto;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(@PathVariable long id,
                                            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
                                            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size)
            throws InvalidParametersException, NoSuchEntityException {
        List<OrderDto> orderDtoList = orderService.getAllByUserId(id, page, size);
        return orderDtoList.stream()
                .peek(orderLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderByUserId(@PathVariable(value = "id") long id,
                                     @PathVariable(value = "orderId") long orderId)
            throws NoSuchEntityException {
        OrderDto orderDto = orderService.getByUserId(id, orderId);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }

}
