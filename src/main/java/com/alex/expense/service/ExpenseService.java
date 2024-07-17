package com.alex.expense.service;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {
    List<ExpenseDto> getAllExpenses(Pageable pageable);
    ExpenseDto getExpenseById(String expenseId);
    void deleteExpenseById(String expenseId);
    ExpenseDto saveExpense(ExpenseDto expenseDto);
    ExpenseDto updateExpense(String expenseId, ExpenseDto expenseDto);
    List<ExpenseDto> findExpensesByCategory(String category, Pageable pageable);
    List<ExpenseDto> findExpensesByName(String name, Pageable pageable);
    List<ExpenseDto> findExpensesByDate(Date startDate, Date endDate, Pageable pageable);
}
