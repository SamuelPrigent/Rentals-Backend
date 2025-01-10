package com.example.back.dto;

public class StringResponseDTO {
    private String message;

    public StringResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
