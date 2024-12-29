// dto: data transfer object
package com.example.learnSpring.dto;

import com.example.learnSpring.model.House;
import java.util.List;

public class AllHousesResponse {
    private List<House> rentals;

    public AllHousesResponse(List<House> rentals) {
        this.rentals = rentals;
    }

    public List<House> getRentals() {
        return rentals;
    }

    public void setRentals(List<House> rentals) {
        this.rentals = rentals;
    }
}
