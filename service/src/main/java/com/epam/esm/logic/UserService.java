package com.epam.esm.logic;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    /**
     * Gets all Users.
     *
     * @param page page number of Users
     * @param size page size
     * @return List of all Tags
     * @throws InvalidParametersException when page or size params are invalid
     */
    List<UserResponseDto> getAll(int page, int size) throws InvalidParametersException;

    /**
     * Gets User by id.
     *
     * @param id User id to search
     * @return founded User
     * @throws NoSuchEntityException when User is not found
     */
    UserResponseDto getById(long id) throws NoSuchEntityException;

    UserResponseDto getByLogin(String login) throws NoSuchEntityException;

    void addSpentMoney(long id, BigDecimal addedValue) throws NoSuchEntityException;

    UserResponseDto register(UserDto userDto);

    void delete(long id);

}
