package com.example.back.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Token d'authentification invalide");
    }
}
