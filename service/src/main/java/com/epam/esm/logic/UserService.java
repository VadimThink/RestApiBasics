package com.epam.esm.logic;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.InvalidParametersException;
import com.epam.esm.exception.NoSuchEntityException;

import java.util.List;

public interface UserService {

    /**
     * Creates new User.
     *
     * @param userDto User to create
     * @return created User
     */
    UserDto create(UserDto userDto);

    /**
     * Gets all Users.
     *
     * @param page page number of Users
     * @param size page size
     * @return List of all Tags
     * @throws InvalidParametersException when page or size params are invalid
     */
    List<UserDto> getAll(int page, int size) throws InvalidParametersException;

    /**
     * Gets User by id.
     *
     * @param id User id to search
     * @return founded User
     * @throws NoSuchEntityException when User is not found
     */
    UserDto getById(long id) throws NoSuchEntityException;
}
