package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
// dto
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.UpdateRentalDTO;
// Rentals service
import com.example.back.service.RentalsService;
// User service
import com.example.back.service.UserService;
// JWT Util
import com.example.back.security.JwtUtil;
// User model
import com.example.back.model.User;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RentalsController {

    @Autowired
    private RentalsService rentalsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

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
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture,
            @RequestHeader("Authorization") String authHeader) throws IOException {

        // Extraire le token du header (enlever "Bearer ")
        String token = authHeader.substring(7);
        // Récupérer l'email depuis le token
        String userEmail = jwtUtil.extractUsername(token);
        // Récupérer l'utilisateur via l'email
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Récupérer l'ID de l'utilisateur
        Long ownerId = user.getId();

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
