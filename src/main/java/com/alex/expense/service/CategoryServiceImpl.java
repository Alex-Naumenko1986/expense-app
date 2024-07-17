package com.alex.expense.service;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.dto.UserDto;
import com.alex.expense.entity.CategoryEntity;
import com.alex.expense.entity.User;
import com.alex.expense.exception.ItemAlreadyExistsException;
import com.alex.expense.exception.ResourceNotFoundException;
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
    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> list =  categoryRepository.findByUserId(userService.getLoggedInUser().getId());
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        CategoryEntity newCategory = mapToEntity(categoryDto);
        if (categoryRepository.existsByNameAndUserId(newCategory.getName(), userService.getLoggedInUser().getId())) {
            throw new ItemAlreadyExistsException(String.format("Category with name %s already exists", newCategory.getName()));
        }
        newCategory = categoryRepository.save(newCategory);
        return mapToDto(newCategory);
    }

    @Override
    public void deleteCategory(String categoryId) {
        CategoryEntity category = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(),
                categoryId).orElseThrow(() -> new ResourceNotFoundException
                (String.format("Category was not found for id %s", categoryId)));
        categoryRepository.delete(category);
    }

    private CategoryEntity mapToEntity(CategoryDto categoryDto) {
        return CategoryEntity.builder().
                name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .categoryIcon(categoryDto.getCategoryIcon())
                .user(userService.getLoggedInUser())
                .categoryId(UUID.randomUUID().toString()).build();
    }

    private CategoryDto mapToDto(CategoryEntity categoryEntity) {
        return CategoryDto.builder().categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .categoryIcon(categoryEntity.getCategoryIcon())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .user(mapToUserDto(categoryEntity.getUser()))
                .build();
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder().
                email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
