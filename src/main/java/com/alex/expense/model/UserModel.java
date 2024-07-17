package com.alex.expense.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Data
public class UserModel {
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter valid email")
    private String email;
    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be at least 5 characters")
    private String password;

    private Integer age = 0;
}
