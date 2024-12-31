package com.example.learnSpring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UpdateHouseDTO {
    private String name;
    private Integer surface;
    private Double price;
    private List<String> picture;
    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;

    // Getters
    public String getName() {
        return name;
    }

    public Integer getSurface() {
        return surface;
    }

    public Double getPrice() {
        return price;
    }

    public List<String> getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    // Request validation (method)
    public void validate() throws IllegalArgumentException {
        if (surface != null && surface <= 0) {
            throw new IllegalArgumentException("La surface doit être > 0");
        }
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Le prix doit être > 0");
        }
    }
}
