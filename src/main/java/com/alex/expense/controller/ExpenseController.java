package com.alex.expense.controller;

import com.alex.expense.dto.ExpenseDto;
import com.alex.expense.io.ExpenseRequest;
import com.alex.expense.io.ExpenseResponse;
import com.alex.expense.mapper.ExpenseRequestMapper;
import com.alex.expense.mapper.ExpenseResponseMapper;
import com.alex.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseRequestMapper expenseRequestMapper;
    private final ExpenseResponseMapper expenseResponseMapper;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getAllExpenses(Pageable page) {
        List<ExpenseDto> expenseDtos = expenseService.getAllExpenses(page);
        return expenseDtos.stream().map(expenseResponseMapper::mapToResponse).toList();
    }

    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseById(@PathVariable String expenseId) {
        ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);
        return expenseResponseMapper.mapToResponse(expenseDto);
    }

    @DeleteMapping("/expenses/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpenseById(@PathVariable String expenseId) {
        expenseService.deleteExpenseById(expenseId);
    }

    @PostMapping("/expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse saveExpense(@RequestBody @Valid ExpenseRequest expenseRequest) {
        ExpenseDto expenseDto = expenseRequestMapper.mapToDto(expenseRequest);
        expenseDto =  expenseService.saveExpense(expenseDto);
        return expenseResponseMapper.mapToResponse(expenseDto);
    }

    @PutMapping("/expenses/{expenseId}")
    public ExpenseResponse updateExpense(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId) {
        ExpenseDto expenseDto = expenseRequestMapper.mapToDto(expenseRequest);
        expenseDto = expenseService.updateExpense(expenseId, expenseDto);
        return expenseResponseMapper.mapToResponse(expenseDto);
    }

    @GetMapping("expenses/category")
    public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable pageable) {
        List<ExpenseDto> expenseDtoList = expenseService.findExpensesByCategory(category, pageable);
        return expenseDtoList.stream().map(expenseResponseMapper::mapToResponse).toList();
    }

    @GetMapping("expenses/name")
    public List<ExpenseResponse> getExpensesByName(@RequestParam String keyword, Pageable pageable) {
        List<ExpenseDto> list = expenseService.findExpensesByName(keyword, pageable);
        return list.stream().map(expenseResponseMapper::mapToResponse).toList();
    }

    @GetMapping("expenses/date")
    public List<ExpenseResponse> getExpensesByDates(@RequestParam(required = false) Date startDate,
                                            @RequestParam(required = false) Date endDate,
                                            Pageable pageable) {
        List<ExpenseDto> list = expenseService.findExpensesByDate(startDate, endDate, pageable);
        return list.stream().map(expenseResponseMapper::mapToResponse).toList();
    }
}
