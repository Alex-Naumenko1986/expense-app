package com.alex.expense.service;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.entity.CategoryEntity;
import com.alex.expense.entity.ExpenseEntity;
import com.alex.expense.exception.ResourceNotFoundException;
import com.alex.expense.mapper.ExpenseDtoMapper;
import com.alex.expense.repository.CategoryRepository;
import com.alex.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseDtoMapper expenseDtoMapper;
    private final UserService userService;

    @Override
    public List<ExpenseDto> getAllExpenses(Pageable pageable) {
        List<ExpenseEntity> listOfExpenses = expenseRepository.findByUserId(userService.getLoggedInUser().getId(), pageable)
                .toList();
        return listOfExpenses.stream().map(expenseDtoMapper::mapToDto).toList();
    }

    @Override
    public ExpenseDto getExpenseById(String expenseId) {
        ExpenseEntity expense = getExpense(expenseId);
        return expenseDtoMapper.mapToDto(expense);

    }

    private ExpenseEntity getExpense(String expenseId) {
        return expenseRepository.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Expense with id %s was not found", expenseId)));
    }

    @Override
    public void deleteExpenseById(String expenseId) {
        ExpenseEntity expense = getExpense(expenseId);
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseDto saveExpense(ExpenseDto expenseDto) {
        Optional<CategoryEntity> categoryEntity = categoryRepository
                .findByUserIdAndCategoryId(userService.getLoggedInUser().getId(),
                expenseDto.getCategoryId());
        if (categoryEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Category with id %s was not found",
                    expenseDto.getCategoryId()));
        }
        expenseDto.setExpenseId(UUID.randomUUID().toString());
        ExpenseEntity expense = expenseDtoMapper.mapToEntity(expenseDto);
        expense.setUser(userService.getLoggedInUser());
        expense.setCategory(categoryEntity.get());
        expense = expenseRepository.save(expense);
        return expenseDtoMapper.mapToDto(expense);
    }

    @Override
    public ExpenseDto updateExpense(String expenseId, ExpenseDto expenseDto) {
        ExpenseEntity existingExpense = getExpense(expenseId);
        existingExpense.setName(Objects.requireNonNullElse(expenseDto.getName(), existingExpense.getName()));
        existingExpense.setAmount(Objects.requireNonNullElse(expenseDto.getAmount(), existingExpense.getAmount()));
        if (expenseDto.getCategoryId() != null) {
            CategoryEntity newCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(),
                    expenseDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %s " +
                    "was not found", expenseDto.getCategoryId())));
            existingExpense.setCategory(newCategory);
        }
        existingExpense.setDescription(Objects.requireNonNullElse(expenseDto.getDescription(), existingExpense.getDescription()));
        existingExpense.setDate(Objects.requireNonNullElse(expenseDto.getDate(), existingExpense.getDate()));
        ExpenseEntity updatedExpense = expenseRepository.save(existingExpense);
        return expenseDtoMapper.mapToDto(updatedExpense);
    }

    @Override
    public List<ExpenseDto> findExpensesByCategory(String category, Pageable pageable) {
        CategoryEntity categoryEntity = categoryRepository.findByNameIgnoreCaseAndUserId(category, userService.getLoggedInUser()
                .getId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format("Category %s was not found", category)));
        List<ExpenseEntity> expenses = expenseRepository.findByUser_IdAndCategory_CategoryId(userService.getLoggedInUser().getId(),
                categoryEntity.getCategoryId(), pageable);
        return expenses.stream().map(expenseDtoMapper::mapToDto).toList();
    }

    @Override
    public List<ExpenseDto> findExpensesByName(String name, Pageable pageable) {
        List<ExpenseEntity> list = expenseRepository.findByUserIdAndNameContainingIgnoreCase(userService.getLoggedInUser().getId(),
                name, pageable).toList();
        return list.stream().map(expenseDtoMapper::mapToDto).toList();
    }

    @Override
    public List<ExpenseDto> findExpensesByDate(Date startDate, Date endDate, Pageable pageable) {
        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        List<ExpenseEntity> expenseList = expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),
                startDate, endDate, pageable).toList();
        return expenseList.stream().map(expenseDtoMapper::mapToDto).toList();
    }
}
