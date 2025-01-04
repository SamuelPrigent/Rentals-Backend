package com.example.back.dto;

public class CreateUserDTO {
    private String email;
    private String name;
    private String password;

    // Constructeur par défaut
    public CreateUserDTO() {
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Validation
    public void validate() {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }
    }
}
