// dto: data transfer object
package com.example.back.dto;

import java.util.List;

public class GetAllRentalDTO {
    private List<GetRentalDTO> rentals;

    // Constructeur
    public GetAllRentalDTO(List<GetRentalDTO> rentals) {
        this.rentals = rentals;
    }

    public List<GetRentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<GetRentalDTO> rentals) {
        this.rentals = rentals;
    }
}
