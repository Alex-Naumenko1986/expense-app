package com.alex.expense.repository;

import com.alex.expense.entity.ExpenseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    Page<ExpenseEntity> findByUserIdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);

    Page<ExpenseEntity> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable pageable);

    Page<ExpenseEntity> findByUserId(Long userId, Pageable pageable);

    Optional<ExpenseEntity> findByUserIdAndExpenseId(Long userId, String expenseId);

    List<ExpenseEntity> findByUser_IdAndCategory_CategoryId(Long id, String categoryId, Pageable pageable);
}
