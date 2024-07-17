package com.alex.expense.repository;

import com.alex.expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUserIdAndNameContaining(Long userId, String name, Pageable pageable);
    Page<Expense> findByUserIdAndDateBetween(Long userId, Date startDate, Date endDate, Pageable pageable);
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    Optional<Expense> findByUserIdAndExpenseId(Long userId, String expenseId);
    List<Expense> findByUser_IdAndCategory_CategoryId(Long id, String categoryId, Pageable pageable);
}
