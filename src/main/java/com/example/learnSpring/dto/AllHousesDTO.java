// dto: data transfer object
package com.example.learnSpring.dto;

import com.example.learnSpring.model.House;
import java.util.List;

public class AllHousesDTO {
    private List<House> rentals; // Format de retour avec la clés : "rentals"

    public AllHousesDTO(List<House> rentals) {
        this.rentals = rentals;
    }

    public List<House> getRentals() {
        return rentals;
    }

    public void setRentals(List<House> rentals) {
        this.rentals = rentals;
    }
}
