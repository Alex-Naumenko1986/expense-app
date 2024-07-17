package com.alex.expense.dto;

import com.alex.expense.io.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDto {
    private String expenseId;
    private String name;
    private String description;
    private BigDecimal amount;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String categoryId;
    private CategoryDto categoryDto;
    private UserDto userDto;
}
