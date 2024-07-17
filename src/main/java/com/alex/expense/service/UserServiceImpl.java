package com.alex.expense.service;

import com.alex.expense.entity.User;
import com.alex.expense.exception.ItemAlreadyExistsException;
import com.alex.expense.exception.ResourceNotFoundException;
import com.alex.expense.model.UserModel;
import com.alex.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;

    @Override
    public User createUser(UserModel user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User readUser() {
        return getLoggedInUser();
    }

    @Override
    public User updateUser(UserModel user) {
        User existingUser = readUser();
        existingUser.setName(Objects.requireNonNullElse(user.getName(), existingUser.getName()));
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        existingUser.setEmail(Objects.requireNonNullElse(user.getEmail(), existingUser.getEmail()));
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        if (user.getAge() != 0) {
            existingUser.setAge(user.getAge());
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s was not found", email)));
    }
}
