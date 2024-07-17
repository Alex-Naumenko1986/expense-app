package com.alex.expense.repository;

import com.alex.expense.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByUserId(Long userId);

    Optional<CategoryEntity> findByNameAndUserId(String name, Long userId);

   Optional<CategoryEntity> findByUserIdAndCategoryId(Long userId, String categoryId);

   boolean existsByNameAndUserId(String name, Long userId);
}