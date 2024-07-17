package com.alex.expense.service;

import com.alex.expense.dto.CategoryDto;
import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.entity.CategoryEntity;
import com.alex.expense.entity.Expense;
import com.alex.expense.exception.ResourceNotFoundException;
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
    private final UserService userService;

    @Override
    public List<ExpenseDto> getAllExpenses(Pageable pageable) {
        List<Expense> listOfExpenses = expenseRepository.findByUserId(userService.getLoggedInUser().getId(), pageable)
                .toList();
        return listOfExpenses.stream().map(this::mapToDto).toList();
    }

    @Override
    public ExpenseDto getExpenseById(String expenseId) {
        Expense expense = getExpense(expenseId);
        return mapToDto(expense);

    }

    private Expense getExpense(String expenseId) {
        return expenseRepository.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Expense with id %s was not found", expenseId)));
    }

    @Override
    public void deleteExpenseById(String expenseId) {
        Expense expense = getExpense(expenseId);
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
        Expense expense = mapToEntity(expenseDto);
        expense.setUser(userService.getLoggedInUser());
        expense.setCategory(categoryEntity.get());
        expense = expenseRepository.save(expense);
        return mapToDto(expense);
    }

    private ExpenseDto mapToDto(Expense expense) {
        return ExpenseDto.builder().
                expenseId(expense.getExpenseId())
                .name(expense.getName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .date(expense.getDate())
                .createdAt(expense.getCreatedAt())
                .updatedAt(expense.getUpdatedAt())
                .categoryDto(mapToCategoryDto(expense.getCategory()))
                .build();
    }

    private CategoryDto mapToCategoryDto(CategoryEntity category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .categoryIcon(category.getCategoryIcon())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    private Expense mapToEntity(ExpenseDto expenseDto) {
        return Expense.builder().
                expenseId(expenseDto.getExpenseId())
                .name(expenseDto.getName())
                .description(expenseDto.getDescription())
                .date(expenseDto.getDate())
                .amount(expenseDto.getAmount())
                .build();
    }

    @Override
    public ExpenseDto updateExpense(String expenseId, ExpenseDto expenseDto) {
        Expense existingExpense = getExpense(expenseId);
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
        Expense updatedExpense = expenseRepository.save(existingExpense);
        return mapToDto(updatedExpense);
    }

    @Override
    public List<ExpenseDto> findExpensesByCategory(String category, Pageable pageable) {
        CategoryEntity categoryEntity = categoryRepository.findByNameAndUserId(category, userService.getLoggedInUser()
                .getId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format("Category %s was not found", category)));
        List<Expense> expenses = expenseRepository.findByUser_IdAndCategory_CategoryId(userService.getLoggedInUser().getId(),
                categoryEntity.getCategoryId(), pageable);
        return expenses.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<ExpenseDto> findExpensesByName(String name, Pageable pageable) {
        List<Expense> list = expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),
                name, pageable).toList();
        return list.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<ExpenseDto> findExpensesByDate(Date startDate, Date endDate, Pageable pageable) {
        if (startDate == null) {
            startDate = new Date(0);
        }

        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }

        List<Expense> expenseList = expenseRepository.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),
                startDate, endDate, pageable).toList();
        return expenseList.stream().map(this::mapToDto).toList();
    }
}
