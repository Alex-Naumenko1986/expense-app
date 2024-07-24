package com.alex.expense.controller;

import com.alex.expense.dto.UserDto;
import com.alex.expense.io.UserRequest;
import com.alex.expense.io.UserResponse;
import com.alex.expense.mapper.UserRequestMapper;
import com.alex.expense.mapper.UserResponseMapper;
import com.alex.expense.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @GetMapping("/profile")
    public UserResponse readUser() {
        UserDto userDto = userService.readUser();
        return userResponseMapper.mapToResponse(userDto);
    }

    @PutMapping("/profile")
    public UserResponse updateUser(@RequestBody UserRequest userRequest) {
        UserDto updatedUser = userService.updateUser(userRequestMapper.mapToDto(userRequest));
        return userResponseMapper.mapToResponse(updatedUser);
    }

    @DeleteMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.deleteUser();
    }
}
