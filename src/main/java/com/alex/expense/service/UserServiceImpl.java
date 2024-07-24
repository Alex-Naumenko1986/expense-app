package com.alex.expense.service;

import com.alex.expense.dto.UserDto;
import com.alex.expense.entity.UserEntity;
import com.alex.expense.exception.ItemAlreadyExistsException;
import com.alex.expense.mapper.UserDtoMapper;
import com.alex.expense.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDto createUser(UserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        UserEntity newUser = userDtoMapper.mapToEntity(user);
        newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return userDtoMapper.mapToDto(newUser);
    }

    @Override
    public UserDto readUser() {
        UserEntity user = getLoggedInUser();
        return userDtoMapper.mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto user) {
        UserEntity existingUser = getLoggedInUser();
        existingUser.setName(Objects.requireNonNullElse(user.getName(), existingUser.getName()));
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail()) &&
                userRepository.existsByEmail(user.getEmail())) {
            throw new ItemAlreadyExistsException(String.format("User with email %s already exists", user.getEmail()));
        }
        existingUser.setEmail(Objects.requireNonNullElse(user.getEmail(), existingUser.getEmail()));
        existingUser.setPassword(user.getPassword() != null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
        existingUser.setAge(Objects.requireNonNullElse(user.getAge(), existingUser.getAge()));
        existingUser = userRepository.save(existingUser);
        return userDtoMapper.mapToDto(existingUser);
    }

    @Override
    public void deleteUser() {
        UserEntity user = getLoggedInUser();
        userRepository.delete(user);
    }

    @Override
    public UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s was not found", email)));
    }
}
