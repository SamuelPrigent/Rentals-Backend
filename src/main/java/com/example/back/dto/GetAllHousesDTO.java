// dto: data transfer object
package com.example.back.dto;

import java.util.List;

import com.example.back.model.House;

public class GetAllHousesDTO {
    private List<House> rentals; // Format de retour avec la clés : "rentals" // changer en rentals TODO

    public GetAllHousesDTO(List<House> rentals) {
        this.rentals = rentals;
    }

    public List<House> getRentals() {
        return rentals;
    }

    public void setRentals(List<House> rentals) {
        this.rentals = rentals;
    }
}
