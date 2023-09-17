package com.backend.exceptions.quantityLimitExceptions;

import org.springframework.http.HttpStatus;

public class QuantityLimitException {
    private final String msg;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public QuantityLimitException(String msg, Throwable throwable, HttpStatus httpStatus) {
        this.msg = msg;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
