package com.alex.expense.mapper;

import com.alex.expense.dto.UserDto;
import com.alex.expense.io.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    UserDto mapToDto(UserRequest userRequest);
}
