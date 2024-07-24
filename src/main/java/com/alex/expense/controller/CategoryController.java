package com.alex.expense.controller;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.io.CategoryRequest;
import com.alex.expense.io.CategoryResponse;
import com.alex.expense.mapper.CategoryRequestMapper;
import com.alex.expense.mapper.CategoryResponseMapper;
import com.alex.expense.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CategoryRequestMapper categoryRequestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        CategoryDto categoryDto = categoryRequestMapper.mapToDto(categoryRequest);
        categoryDto = categoryService.saveCategory(categoryDto);
        return categoryResponseMapper.mapToResponse(categoryDto);
    }

    @GetMapping
    public List<CategoryResponse> getAllUserCategories() {
        List<CategoryDto> listOfCategories = categoryService.getAllCategories();
        return listOfCategories.stream().map(categoryResponseMapper::mapToResponse).toList();
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
    }
}
