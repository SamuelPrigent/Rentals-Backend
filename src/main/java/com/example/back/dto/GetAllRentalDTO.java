// dto: data transfer object
package com.example.back.dto;

import java.util.List;

import com.example.back.model.Rentals;

public class GetAllRentalDTO {
    private List<Rentals> rentals; // Format de retour avec la clés : "rentals" // changer en rentals TODO

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
