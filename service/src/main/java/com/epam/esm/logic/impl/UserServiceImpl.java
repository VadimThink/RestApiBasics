package com.epam.esm.logic.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.UserService;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public UserDto create(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        return userMapper.mapToDto(userRepository.create(user));
    }

    @Override
    public List<UserDto> getAll(int page, int size) throws InvalidParametersException {
        /*Pageable pageRequest;
        try {
            pageRequest = PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new InvalidParametersException("invalid.pagination");
        }todo*/
        return userMapper.mapListToDto(userRepository.getAll(/*pageRequest*/));
    }

    @Override
    public UserDto getById(long id) throws NoSuchEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        return userMapper.mapToDto(user);
    }
}
