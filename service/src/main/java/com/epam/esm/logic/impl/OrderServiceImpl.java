package com.epam.esm.logic.impl;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.OrderService;
import com.epam.esm.logic.UserService;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GiftCertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            GiftCertificateRepository certificateRepository,
                            UserRepository userRepository, UserService userService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional(rollbackFor = NoSuchEntityException.class)
    public OrderDto create(CreateOrderDto createOrderDto) throws NoSuchEntityException {
        Order order = new Order();
        User user = userRepository.findById(createOrderDto.getUserId()).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        order.setUser(user);
        GiftCertificate certificate = certificateRepository.findById(createOrderDto.getCertificateId())
                .orElseThrow(() -> new NoSuchEntityException("certificate.not.found"));
        order.setGiftCertificate(certificate);
        order.setCost(certificate.getPrice());
        userService.addSpentMoney(createOrderDto.getUserId(), order.getCost());
        return orderMapper.mapToDto(orderRepository.create(order));
    }

    @Override
    @Transactional
    public List<OrderDto> getAllByUserId(long userId, int page, int size) throws NoSuchEntityException {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        return orderMapper.mapListToDto(orderRepository.getAllByUserId(userId, page, size));
    }

    @Override
    @Transactional
    public OrderDto getByUserId(long userId, long orderId) throws NoSuchEntityException {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchEntityException("order.not.found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        List<Order> orders = user.getOrders();
        if (orders == null || orders.isEmpty() || !orders.contains(order)) {
            throw new NoSuchEntityException("order.not.found");
        }
        return orderMapper.mapToDto(order);
    }
}
