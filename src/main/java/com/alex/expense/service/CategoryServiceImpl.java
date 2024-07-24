package com.alex.expense.service;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.entity.CategoryEntity;
import com.alex.expense.exception.ItemAlreadyExistsException;
import com.alex.expense.exception.ResourceNotFoundException;
import com.alex.expense.mapper.CategoryDtoMapper;
import com.alex.expense.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> list =  categoryRepository.findByUserId(userService.getLoggedInUser().getId());
        return list.stream().map(categoryDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        CategoryEntity newCategory = categoryDtoMapper.mapToEntity(categoryDto);
        if (categoryRepository.existsByNameIgnoreCaseAndUserId(newCategory.getName(), userService.getLoggedInUser().getId())) {
            throw new ItemAlreadyExistsException(String.format("Category with name %s already exists", newCategory.getName()));
        }
        newCategory.setUser(userService.getLoggedInUser());
        newCategory.setCategoryId(UUID.randomUUID().toString());
        newCategory = categoryRepository.save(newCategory);
        return categoryDtoMapper.mapToDto(newCategory);
    }

    @Override
    public void deleteCategory(String categoryId) {
        CategoryEntity category = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(),
                categoryId).orElseThrow(() -> new ResourceNotFoundException
                (String.format("Category was not found for id %s", categoryId)));
        categoryRepository.delete(category);
    }
}

