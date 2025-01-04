package com.example.back.dto;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import java.util.List;

// TODO pas normal que l'on puisse edit le ownerId 

public class UpdateRentalDTO {
    private String name;
    private Integer surface;
    private Double price;
    private String picture;
    private String description;

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

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
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

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
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
