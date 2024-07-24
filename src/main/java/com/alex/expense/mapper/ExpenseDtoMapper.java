package com.alex.expense.mapper;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryDtoMapper.class})
public interface ExpenseDtoMapper {
    @Mapping(source = "category", target = "categoryDto")
    ExpenseDto mapToDto(ExpenseEntity expenseEntity);

    @Mapping(source = "categoryDto", target = "category")
    ExpenseEntity mapToEntity(ExpenseDto expenseDto);
}
