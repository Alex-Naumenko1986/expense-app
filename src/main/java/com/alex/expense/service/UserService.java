package com.alex.expense.service;

import com.alex.expense.dto.UserDto;
import com.alex.expense.entity.UserEntity;

public interface UserService {
    UserDto createUser(UserDto user);

    UserDto readUser();

    UserDto updateUser(UserDto user);
    void deleteUser();

    UserEntity getLoggedInUser();
}
