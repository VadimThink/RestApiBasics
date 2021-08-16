package com.epam.esm.logic.impl;

import com.epam.esm.constant.Status;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.logic.UserService;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserResponseMapper;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserResponseMapper userResponseMapper,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    @Transactional
    public List<UserResponseDto> getAll(int page, int size) {
        return userResponseMapper.mapListToDto(userRepository.getAll(page, size));
    }

    @Override
    @Transactional
    public UserResponseDto getById(long id) throws NoSuchEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        return userResponseMapper.mapToDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto getByLogin(String login) throws NoSuchEntityException{
        User user = userRepository.findByField("login", login)
                .orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        return userResponseMapper.mapToDto(user);
    }

    @Override
    public void addSpentMoney(long id, BigDecimal addedValue) throws NoSuchEntityException {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchEntityException("user.not.found"));
        user.setSpentMoney(user.getSpentMoney().add(addedValue));
        userRepository.update(user);
    }

    @Override
    @Transactional
    public UserResponseDto register(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        if (userRepository.findByField("login", user.getLogin()).isPresent())
            throw new DuplicateException("user.exist");
        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new NoSuchEntityException("role.not.found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRoles(userRoles);

        return userResponseMapper.mapToDto(userRepository.create(user));
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() ->new NoSuchEntityException("user.not.found"));
        userRepository.delete(user);
    }

}
