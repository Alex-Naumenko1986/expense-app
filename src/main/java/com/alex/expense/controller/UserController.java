package com.alex.expense.controller;

import com.alex.expense.entity.User;
import com.alex.expense.model.UserModel;
import com.alex.expense.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser() {
        return new ResponseEntity<>(userService.readUser(), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel userModel) {
        return new ResponseEntity<>(userService.updateUser(userModel), HttpStatus.OK);
    }

    @DeleteMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.deleteUser();
    }
}
