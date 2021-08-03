package com.epam.esm.logic.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.UserService;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) throws DuplicateException {
        User user = userMapper.mapToEntity(userDto);
        if (userRepository.findByField("name", user.getName()).isPresent())
            throw new DuplicateException("user.exist");
        return userMapper.mapToDto(userRepository.create(user));
    }

    @Override
    @Transactional
    public List<UserDto> getAll(int page, int size) {
        return userMapper.mapListToDto(userRepository.getAll(page, size));
    }

    @Override
    @Transactional
    public UserDto getById(long id) throws NoSuchEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        return userMapper.mapToDto(user);
    }

    @Override
    public void addSpentMoney(long id, BigDecimal addedValue) throws NoSuchEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        user.setSpentMoney(user.getSpentMoney().add(addedValue));
        userRepository.update(user);
    }

    @Override
    public UserDto findUserWithMaxSpentMoney() {
        return userMapper.mapToDto(userRepository.findUserWithMaxSpentMoney());
    }


}
