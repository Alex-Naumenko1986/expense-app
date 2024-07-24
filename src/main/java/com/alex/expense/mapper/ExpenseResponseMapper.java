package com.alex.expense.mapper;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.io.ExpenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryResponseMapper.class})
public interface ExpenseResponseMapper {
    @Mapping(source = "categoryDto", target = "category")
    ExpenseResponse mapToResponse(ExpenseDto expenseDto);
}
