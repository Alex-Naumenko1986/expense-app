package com.alex.expense.controller;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.io.CategoryRequest;
import com.alex.expense.io.CategoryResponse;
import com.alex.expense.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryDto categoryDto = mapToDto(categoryRequest);
        categoryDto = categoryService.saveCategory(categoryDto);
        return mapToResponse(categoryDto);
    }

    @GetMapping
    public List<CategoryResponse> getAllUserCategories() {
        List<CategoryDto> listOfCategories = categoryService.getAllCategories();
        return listOfCategories.stream().map(this::mapToResponse).toList();
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.deleteCategory(categoryId);
    }

    private CategoryResponse mapToResponse(CategoryDto categoryDto) {
        return CategoryResponse.builder().categoryId(categoryDto.getCategoryId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .categoryIcon(categoryDto.getCategoryIcon())
                .createdAt(categoryDto.getCreatedAt())
                .updatedAt(categoryDto.getUpdatedAt())
                .build();
    }

    private CategoryDto mapToDto(CategoryRequest categoryRequest) {
        return CategoryDto.builder().name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .categoryIcon(categoryRequest.getIcon())
                .build();
    }
}
