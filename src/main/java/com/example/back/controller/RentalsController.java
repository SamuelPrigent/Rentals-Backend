package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.UpdateRentalDTO;
import com.example.back.exception.ResourceNotFoundException;
import com.example.back.model.Rentals;
import com.example.back.repository.RentalRepository;
import com.example.back.service.CloudinaryService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class RentalsController {

    // --- Database tables ---
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // get all
    @GetMapping("/rentals")
    public GetAllRentalDTO getAllRentals() {
        // besoins logique métier (services)
        // bdd
        return new GetAllRentalDTO(rentalRepository.findAll()); //
    }

    // get one
    @GetMapping("/rentals/{id}")
    public ResponseEntity<?> getOneRental(@PathVariable Long id) {
        return rentalRepository.findById(id)
                .map(rental -> ResponseEntity.ok(new GetRentalDTO(rental)))
                .orElse(ResponseEntity.notFound().build());
    }

    // create one rental
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRentalDTO> createRental(@ModelAttribute CreateRentalDTO request) throws IOException {
        // Création d'une entité rental
        Rentals rental = new Rentals();
        // Upload de l'image sur Cloudinary
        String pictureUrl = null;
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            pictureUrl = cloudinaryService.uploadFile(request.getPicture());
        }
        // Attribution des données de la request à l'entité
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(pictureUrl);
        rental.setDescription(request.getDescription());
        rental.setOwnerId(request.getOwnerId());
        // Sauvegarde l'entité en base de données
        Rentals savedRental = rentalRepository.save(rental);
        return ResponseEntity.ok(new GetRentalDTO(savedRental));
    }

    // update one rental
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRental(@PathVariable Long id, @ModelAttribute UpdateRentalDTO request) {
        // Validation des données
        request.validate();
        // Recherche de la maison
        Rentals rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rental found with id: " + id));
        // Met à jour les données de l'entité
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        rental.setOwnerId(request.getOwnerId());
        // Sauvegarde les modifications
        Rentals updatedRental = rentalRepository.save(rental);
        return ResponseEntity.ok(new GetRentalDTO(updatedRental));
    }

    // DEV ROUTE ===================================
    // ------------ delete one rental
    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<?> deleteRental(@PathVariable Long id) {
        return rentalRepository.findById(id)
                .map(rental -> {
                    rentalRepository.delete(rental);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.status(404)
                        .body("No rental found with ID: " + id));
    }

}
