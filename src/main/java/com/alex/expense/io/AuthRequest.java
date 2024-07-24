package com.alex.expense.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Field email should not be blank")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Field password should not be blank")
    private String password;
}
