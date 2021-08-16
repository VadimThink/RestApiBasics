package com.epam.esm.mapper;

import com.epam.esm.dto.UserResponseDto;
import com.epam.esm.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends BaseMapper<UserResponseDto, User> {
}
