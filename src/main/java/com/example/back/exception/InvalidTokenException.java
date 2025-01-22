package com.example.back.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token invalide ou expiré");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
