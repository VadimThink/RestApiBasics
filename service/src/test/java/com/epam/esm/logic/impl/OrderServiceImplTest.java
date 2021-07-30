package com.epam.esm.logic.impl;

import com.epam.esm.dto.CreateOrderDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.logic.UserService;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderServiceImpl.class)
public class OrderServiceImplTest {

    private static final long ID = 1;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private static final int USER_ID = 1;
    private static final int CERTIFICATE_ID = 1;
    private static final int ORDER_ID = 1;

    private static final CreateOrderDto CREATE_ORDER_DTO = new CreateOrderDto(1 ,1);

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private GiftCertificateRepository certificateRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private OrderMapper orderMapper;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void testCreateWhenAllGood(){
        when(userRepository.findById(CREATE_ORDER_DTO.getUserId())).thenReturn(Optional.of(new User()));
        when(certificateRepository.findById(CREATE_ORDER_DTO.getCertificateId()))
                .thenReturn(Optional.of(new GiftCertificate()));
        when(orderRepository.create(any())).thenReturn(new Order());
        when(orderMapper.mapToDto(new Order()))
                .thenReturn(new OrderDto(1, new UserDto(),
                        new GiftCertificateDto(), ZonedDateTime.now(), new BigDecimal(1)));
        assertNotNull(orderService.create(CREATE_ORDER_DTO));
    }

    @Test
    public void testGetAllByUserId(){
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        when(orderRepository.getAllByUserId(USER_ID, DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).thenReturn(new ArrayList<>());
        when(orderMapper.mapListToDto(new ArrayList<>())).thenReturn(new ArrayList<>());
        assertNotNull(orderService.getAllByUserId(USER_ID, DEFAULT_PAGE, DEFAULT_PAGE_SIZE));
    }

    @Test
    public void testGetByUserId(){
        Order order = new Order();
        order.setUser(new User());
        order.setCost(new BigDecimal(1));
        order.setGiftCertificate(new GiftCertificate());
        User user = new User();
        user.setOrders(Collections.singletonList(order));
        when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(order));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        when(orderMapper.mapToDto(order)).thenReturn(orderDto);
        assertNotNull(orderService.getByUserId(USER_ID, ORDER_ID));
    }

}