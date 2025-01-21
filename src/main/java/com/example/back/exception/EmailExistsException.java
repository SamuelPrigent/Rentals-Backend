package com.example.back.exception;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException() {
        super("Un compte existe déjà avec cet email");
    }
}
