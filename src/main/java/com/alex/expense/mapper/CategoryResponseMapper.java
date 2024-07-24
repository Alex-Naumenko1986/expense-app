package com.alex.expense.mapper;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.io.CategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryResponseMapper {
    CategoryResponse mapToResponse(CategoryDto categoryDto);

    CategoryDto mapToDto(CategoryResponse categoryResponse);
}
