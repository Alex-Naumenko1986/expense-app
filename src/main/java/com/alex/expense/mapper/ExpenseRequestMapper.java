package com.alex.expense.mapper;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.io.ExpenseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseRequestMapper {
    ExpenseDto mapToDto(ExpenseRequest request);
}
