package com.backend.exceptions.quantityLimitExceptions;

import org.springframework.http.HttpStatus;

public class QuantityLimitNotFoundException extends RuntimeException {
    public QuantityLimitNotFoundException(String msg) {
        super(msg);
    }

    public QuantityLimitNotFoundException(Throwable cause) {
        super(cause);
    }

    public QuantityLimitNotFoundException(String message, Throwable cause, HttpStatus httpStatus) {
    }
}
