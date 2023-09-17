package com.backend.exceptions.quantityLimitExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuantityLimitExceptionHandler {
    @ExceptionHandler(value = {QuantityLimitNotFoundException.class})
    public ResponseEntity<Object> handleQuantityLimitNotFoundException
            (QuantityLimitNotFoundException quantityLimitNotFoundException) {
        QuantityLimitException quantityLimitException = new QuantityLimitException(
                quantityLimitNotFoundException.getMessage(),
                quantityLimitNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(quantityLimitException, HttpStatus.NOT_FOUND);
    }
}
