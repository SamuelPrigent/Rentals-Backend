package com.example.back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.example.back.dto.StringResponseDTO;
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
@Tag(name = "Rentals", description = "API de gestion des locations")
public class RentalsController {

    @Autowired
    private RentalsService rentalsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Operation(summary = "Get all rentals", description = "Retourne la liste de toutes les Rentals disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    // get all rentals
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalDTO> getAllRentals() {
        return ResponseEntity.ok(rentalsService.getAll());
    }

    // get rentals by id
    @GetMapping("/rentals/{id}")
    public ResponseEntity<GetRentalDTO> getOneRental(@PathVariable Long id) {
        return rentalsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // create rental
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Integer surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture,
            @RequestHeader("Authorization") String authHeader) throws IOException {

        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        User user = userService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long ownerId = user.getId();

        CreateRentalDTO request = new CreateRentalDTO();
        request.setName(name);
        request.setSurface(surface);
        request.setPrice(price);
        request.setPicture(picture);
        request.setDescription(description);
        request.setOwnerId(ownerId);

        StringResponseDTO response = rentalsService.create(request);

        return ResponseEntity.ok(response);
    }

    // put rentals by id (if owner)
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StringResponseDTO> updateRental(
            @PathVariable Long id,
            @ModelAttribute UpdateRentalDTO request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        StringResponseDTO response = rentalsService.update(id, request, userEmail);
        return ResponseEntity.ok(response);
    }
}
