package com.alex.expense.mapper;

import com.alex.expense.dto.UserDto;
import com.alex.expense.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserEntity mapToEntity(UserDto userDto);

    UserDto mapToDto(UserEntity userEntity);
}
