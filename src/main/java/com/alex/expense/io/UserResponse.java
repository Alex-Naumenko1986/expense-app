package com.alex.expense.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String name;

    private String email;

    private Integer age;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
