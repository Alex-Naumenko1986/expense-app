package com.alex.expense.io;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name should not be blank")
    private String name;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter valid email")
    private String email;
    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be at least 5 characters")
    private String password;
    @NotNull(message = "Age should not be null")
    @PositiveOrZero
    private Integer age;
}
