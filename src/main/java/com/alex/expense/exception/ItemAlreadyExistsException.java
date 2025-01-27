package com.alex.expense.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ItemAlreadyExistsException extends RuntimeException{

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
