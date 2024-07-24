package com.alex.expense.mapper;

import com.alex.expense.dto.UserDto;
import com.alex.expense.io.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserResponse mapToResponse(UserDto userDto);
}
