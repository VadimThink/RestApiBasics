package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.link.OrderLinkProvider;
import com.epam.esm.link.UserLinkProvider;
import com.epam.esm.logic.OrderService;
import com.epam.esm.logic.UserService;
import com.epam.esm.security.UserAccessService;
import com.epam.esm.validation.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final UserAccessService userAccessService;

    @Autowired
    public UserController(UserService userService, OrderService orderService,
                          UserLinkProvider userLinkProvider, OrderLinkProvider orderLinkProvider,
                          UserAccessService userAccessService) {
        this.userService = userService;
        this.orderService = orderService;
        this.userLinkProvider = userLinkProvider;
        this.orderLinkProvider = orderLinkProvider;
        this.userAccessService = userAccessService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("/signup")
    public UserDto signup(@RequestBody @Valid UserDto userDto, BindingResult bindingResult)
            throws DuplicateException {
        ValidationExceptionChecker.checkDtoValidation(bindingResult);
        UserDto newUserDto = userService.register(userDto);
        userLinkProvider.provideLinks(newUserDto);
        return newUserDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size)
            throws InvalidParametersException {
        RequestParametersValidator.validatePaginationParams(page, size);
        List<UserDto> userDtoList = userService.getAll(page, size);
        return userDtoList.stream()
                .peek(userLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(HttpServletRequest httpServletRequest, @PathVariable long id) throws NoSuchEntityException {
        RequestParametersValidator.validateId(id);
        userAccessService.checkAccess(httpServletRequest, id);
        UserDto userDto = userService.getById(id);
        userLinkProvider.provideLinks(userDto);
        return userDto;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(
            @PathVariable long id,
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size,
            HttpServletRequest httpServletRequest)
            throws InvalidParametersException, NoSuchEntityException {
        RequestParametersValidator.validateId(id);
        userAccessService.checkAccess(httpServletRequest, id);
        RequestParametersValidator.validatePaginationParams(page, size);
        List<OrderDto> orderDtoList = orderService.getAllByUserId(id, page, size);
        return orderDtoList.stream()
                .peek(orderLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderByUserId(
            @PathVariable(value = "id") long id,
            @PathVariable(value = "orderId") long orderId,
            HttpServletRequest httpServletRequest)
            throws NoSuchEntityException {
        RequestParametersValidator.validateId(id);
        userAccessService.checkAccess(httpServletRequest, id);
        RequestParametersValidator.validateId(orderId);
        OrderDto orderDto = orderService.getByUserId(id, orderId);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }

}
