package com.alex.expense.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    @NotBlank(message = "Field name should not be blank")
    private String name;
    private String description;
    @NotBlank(message = "Field icon should not be blank")
    private String icon;
}
