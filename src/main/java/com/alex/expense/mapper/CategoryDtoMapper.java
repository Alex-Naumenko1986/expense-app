package com.alex.expense.mapper;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface CategoryDtoMapper {
    CategoryDto mapToDto(CategoryEntity categoryEntity);

    CategoryEntity mapToEntity(CategoryDto categoryDto);
}
