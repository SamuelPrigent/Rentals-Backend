package com.example.back.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("Email ou mot de passe incorrect");
    }
}
