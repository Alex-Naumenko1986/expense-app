package com.alex.expense.mapper;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.io.CategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryRequestMapper {
    CategoryDto mapToDto(CategoryRequest categoryRequest);
}
