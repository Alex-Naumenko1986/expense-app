package com.alex.expense.service;

import com.alex.expense.entity.User;
import com.alex.expense.model.UserModel;

public interface UserService {
    User createUser(UserModel user);
    User readUser();
    User updateUser(UserModel user);
    void deleteUser();
    User getLoggedInUser();
}
