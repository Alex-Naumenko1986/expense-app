package com.alex.expense.controller;

import com.alex.expense.dto.UserDto;
import com.alex.expense.entity.JwtResponse;
import com.alex.expense.io.AuthRequest;
import com.alex.expense.io.UserRequest;
import com.alex.expense.io.UserResponse;
import com.alex.expense.mapper.UserRequestMapper;
import com.alex.expense.mapper.UserResponseMapper;
import com.alex.expense.security.CustomUserDetailsService;
import com.alex.expense.service.UserService;
import com.alex.expense.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;

    private final UserService userService;

    private final CustomUserDetailsService userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserResponseMapper userResponseMapper;

    private final UserRequestMapper userRequestMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
       final String token = jwtTokenUtil.generateToken(userDetails);
       return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {
        try{
            authManager.authenticate
                    (new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("User disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest user) {
        UserDto createdUser = userService.createUser(userRequestMapper.mapToDto(user));
        return userResponseMapper.mapToResponse(createdUser);
    }
}
