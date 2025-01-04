package com.example.back.dto;

import com.example.back.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.format.DateTimeFormatter;

public class GetUserDTO {
    private Long id;
    private String name;
    private String email;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    // Constructeur par défaut
    public GetUserDTO() {
    }

    // Constructeur à partir d'une entité User
    public GetUserDTO(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt().format(formatter);
        this.updatedAt = user.getUpdatedAt().format(formatter);
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
