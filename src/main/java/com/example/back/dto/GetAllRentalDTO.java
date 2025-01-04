// dto: data transfer object
package com.example.back.dto;

import java.util.List;

import com.example.back.model.Rentals;

// C'est dans mes DTO que je dois normalment importer la BDD ?
// 

public class GetAllRentalDTO {
    private List<Rentals> rentals;

    // Constructeur
    public GetAllRentalDTO(List<Rentals> rentals) {
        this.rentals = rentals;
    }

    public List<Rentals> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rentals> rentals) {
        this.rentals = rentals;
    }
}
