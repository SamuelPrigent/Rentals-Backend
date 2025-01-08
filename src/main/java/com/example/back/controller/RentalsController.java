package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
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

    @Autowired
    private RentalsService rentalsService;

    // get all
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalDTO> getAllRentals() {
        return ResponseEntity.ok(rentalsService.getAll());
    }

    // get one
    @GetMapping("/rentals/{id}")
    public ResponseEntity<GetRentalDTO> getOneRental(@PathVariable Long id) {
        return rentalsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // create one rental
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRentalDTO> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") Double price,
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("description") String description,
            @RequestParam("owner_id") Long ownerId) throws IOException {
        CreateRentalDTO request = new CreateRentalDTO();
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setPicture(picture);
        request.setDescription(description);
        request.setOwnerId(ownerId);
        GetRentalDTO createdRental = rentalsService.create(request);
        return ResponseEntity.ok(createdRental);
    }

    // update one rental
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRentalDTO> updateRental(@PathVariable Long id, @ModelAttribute UpdateRentalDTO request) {
        GetRentalDTO updatedRental = rentalsService.update(id, request);
        return ResponseEntity.ok(updatedRental);
    }
}
