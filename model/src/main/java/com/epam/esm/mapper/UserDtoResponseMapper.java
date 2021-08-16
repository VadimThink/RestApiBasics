package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoResponseMapper extends BaseMapper<UserResponseDto, UserDto> {
}
