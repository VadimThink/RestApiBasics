package com.epam.esm.logic.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final BigDecimal SPENT_MONEY = new BigDecimal(0);

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    private static final UserDto USER_DTO = new UserDto(ID,NAME, SPENT_MONEY );

    private static User USER = new User();

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @BeforeClass
    public static void init(){
        USER.setId(ID);
        USER.setName(NAME);
        USER.setOrders(new ArrayList<>());
        USER.setSpentMoney(SPENT_MONEY);
    }

    @Test
    public void testCreateWhenAllGood() {
        when(userMapper.mapToEntity(USER_DTO)).thenReturn(USER);
        when(userRepository.findByField("name", USER.getName())).thenReturn(Optional.empty());
        when(userRepository.create(USER)).thenReturn(USER);
        when(userMapper.mapToDto(USER)).thenReturn(USER_DTO);
        assertNotNull(userService.create(USER_DTO));
    }

    @Test
    public void testGetAll() {
        when(userRepository.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE)).thenReturn(Collections.singletonList(USER));
        when(userMapper.mapListToDto(Collections.singletonList(USER))).thenReturn(Collections.singletonList(USER_DTO));
        assertNotNull(userService.getAll(DEFAULT_PAGE, DEFAULT_PAGE_SIZE));
    }

    @Test
    public void testGetById() {
        when(userRepository.findById(ID)).thenReturn(Optional.ofNullable(USER));
        when(userMapper.mapToDto(USER)).thenReturn(USER_DTO);
        assertNotNull(userService.getById(ID));
    }

}