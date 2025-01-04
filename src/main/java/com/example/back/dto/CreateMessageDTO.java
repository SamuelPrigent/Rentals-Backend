package com.example.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateMessageDTO {
    private String message;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("rental_id")
    private Long rentalId;

    // Constructeur par défaut
    public CreateMessageDTO() {
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getRentalId() {
        return rentalId;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    // Validation
    public void validate() {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Le message est obligatoire");
        }
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire");
        }
        if (rentalId == null) {
            throw new IllegalArgumentException("L'ID du rental est obligatoire");
        }
    }
}
