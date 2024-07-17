package com.alex.expense.service;

import com.alex.expense.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    CategoryDto saveCategory(CategoryDto categoryDto);
    void deleteCategory(String categoryId);
}
