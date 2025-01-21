package com.example.back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import com.example.back.service.RentalsService; // Rentals service
import com.example.back.service.UserService; // User service
import com.example.back.security.JwtUtil; // JWT Util
import com.example.back.model.User; // User model

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

    /**
     * All rentals
     * GET /api/rentals
     */
    @Operation(summary = "Get all rentals", description = "Retourne la liste de toutes les Rentals disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des locations récupérée avec succès"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @GetMapping("/rentals")
    public ResponseEntity<GetAllRentalDTO> getAllRentals() {
        return ResponseEntity.ok(rentalsService.getAll());
    }

    /**
     * GET /api/rentals/{id}
     */
    @Operation(summary = "Get rental by ID", description = "Retourne une location spécifique par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location trouvée avec succès"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @GetMapping("/rentals/{id}")
    public ResponseEntity<GetRentalDTO> getOneRental(
            @Parameter(description = "ID de la location", required = true) @PathVariable Long id) {
        return rentalsService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/rentals
     */
    @Operation(summary = "Create rental", description = "Crée une nouvelle location")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> createRental(
            @Parameter(description = "Nom de la location", required = true) @RequestParam("name") String name,
            @Parameter(description = "Surface en m²", required = true) @RequestParam("surface") Integer surface,
            @Parameter(description = "Prix de la location", required = true) @RequestParam("price") Double price,
            @Parameter(description = "Description de la location", required = true) @RequestParam("description") String description,
            @Parameter(description = "Image de la location", required = true) @RequestParam("picture") MultipartFile picture,
            @Parameter(description = "Token d'authentification (Bearer)", required = true) @RequestHeader("Authorization") String authHeader)
            throws IOException {

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

    /**
     * PUT /api/rentals/{id}
     */
    @Operation(summary = "Update rental", description = "Met à jour une location existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié"),
            @ApiResponse(responseCode = "404", description = "Location non trouvée")
    })
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StringResponseDTO> updateRental(
            @Parameter(description = "ID de la location à mettre à jour", required = true) @PathVariable Long id,
            @Parameter(description = "Données de mise à jour de la location", required = true) @ModelAttribute UpdateRentalDTO request,
            @Parameter(description = "Token d'authentification (Bearer)", required = true) @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        StringResponseDTO response = rentalsService.update(id, request, userEmail);
        return ResponseEntity.ok(response);
    }
}
