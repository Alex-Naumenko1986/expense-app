package com.alex.expense.io;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {
    @NotBlank(message = "Expense name should not be blank")
    @Size(min = 3, message = "Expense name must be at least 3 characters")
    private String name;
    @NotNull(message = "Amount field should not be null")
    private String description;
    @NotNull(message = "Amount field should not be null")
    private BigDecimal amount;
    @NotBlank(message = "Category id should not be blank")
    private String categoryId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date must not be null")
    private Date date;
}
