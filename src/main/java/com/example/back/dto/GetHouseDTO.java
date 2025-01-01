package com.example.back.dto;

import com.example.back.model.House;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
// import java.time.LocalDate;

public class GetHouseDTO {
    private Long id;
    private String name;
    private Integer surface;
    private Double price;
    private List<String> picture;
    private String description;

    // ==== JsonProperty ====

    @JsonProperty("owner_id") // ownerId (BDD) => owner_id (JSON)
    private Long ownerId;

    @JsonProperty("created_at") // createdAt (BDD) => "created_at" (JSON)
    private String createdAt;

    @JsonProperty("updated_at") // updatedAt (BDD) => "updated_at" (JSON)
    private String updatedAt;

    // Constructeur convertit entité House dans le format DTO souhaité
    public GetHouseDTO(House house) {
        this.id = house.getId();
        this.name = house.getName();
        this.surface = house.getSurface();
        this.price = house.getPrice();
        this.picture = house.getPicture();
        this.description = house.getDescription();
        this.ownerId = house.getOwnerId();
        this.createdAt = house.getCreatedAt() != null ? house.getCreatedAt().toString() : null;
        this.updatedAt = house.getUpdatedAt() != null ? house.getUpdatedAt().toString() : null;
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

    public List<String> getPicture() {
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
