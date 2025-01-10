package com.example.back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedRentalAccessException extends RuntimeException {
    public UnauthorizedRentalAccessException(String message) {
        super(message);
    }
}
