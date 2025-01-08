package com.example.back.dto;

import com.example.back.model.Rentals;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRentalDTO {
    private Long id;
    private String name;
    private Integer surface;
    private Double price;
    private String picture;
    private String description;

    // ==== JsonProperty ====

    @JsonProperty("owner_id") // ownerId (BDD) => owner_id (JSON)
    private Long ownerId;

    @JsonProperty("created_at") // createdAt (BDD) => "created_at" (JSON)
    private String createdAt;

    @JsonProperty("updated_at") // updatedAt (BDD) => "updated_at" (JSON)
    private String updatedAt;

    // Constructeur convertit entité House dans le format DTO souhaité
    public GetRentalDTO(Rentals rental) {
        this.id = rental.getId();
        this.name = rental.getName();
        this.surface = rental.getSurface();
        this.price = rental.getPrice();
        this.picture = rental.getPicture();
        this.description = rental.getDescription();
        this.ownerId = rental.getOwner() != null ? rental.getOwner().getId() : null;
        this.createdAt = rental.getCreatedAt() != null ? rental.getCreatedAt().toString() : null;
        this.updatedAt = rental.getUpdatedAt() != null ? rental.getUpdatedAt().toString() : null;
    }

    // Getters
    public Long getId() {
        return id;
    }

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

    public Long getOwnerId() {
        return ownerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
