package com.backend.exceptions.productNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ProductException productException = new ProductException(
                productNotFoundException.getMessage(),
                productNotFoundException.getCause(),
                notFound,
                ZonedDateTime.now(Clock.systemDefaultZone())
        );
        return new ResponseEntity<>(productException, notFound);
    }
}
