package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
// Dto
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.UpdateRentalDTO;
// Rentals service
import com.example.back.service.RentalsService;

@RestController
@RequestMapping("/api")
public class RentalsController {

    @Autowired // service métier
    private RentalsService rentalsService;

    // get all
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalDTO> getAllRentals() {
        return ResponseEntity.ok(rentalsService.getAllRentals());
    }

    // get one
    @GetMapping("/rentals/{id}")
    public ResponseEntity<GetRentalDTO> getOneRental(@PathVariable Long id) {
        return rentalsService.getOneRental(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // create one rental
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRentalDTO> createRental(@ModelAttribute CreateRentalDTO request) throws IOException {
        GetRentalDTO createdRental = rentalsService.createRental(request);
        return ResponseEntity.ok(createdRental);
    }

    // update one rental
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRentalDTO> updateRental(@PathVariable Long id, @ModelAttribute UpdateRentalDTO request) {
        GetRentalDTO updatedRental = rentalsService.updateRental(id, request);
        return ResponseEntity.ok(updatedRental);
    }

}
