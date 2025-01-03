package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.UpdateRentalDTO;
import com.example.back.exception.ResourceNotFoundException;
import com.example.back.model.Rentals;
import com.example.back.repository.RentalRepository;

// REST API (détecté par spring)
// transformé en rentalsController TODO
@RestController
@RequestMapping("/api")
public class RentalsController {

    // --- Database tables ---
    @Autowired
    private RentalRepository houseRepository;

    // ======== Hello (check api) ========
    @GetMapping("/")
    public ResponseEntity<String> getResponse() {
        return ResponseEntity.ok("Rentals API");
    }

    // ======== Houses ========
    // get all
    @GetMapping("/rentals")
    public GetAllRentalDTO getAllHouses() {
        // besoins logique métier (services)
        // bdd
        return new GetAllRentalDTO(houseRepository.findAll()); //
        //

    }

    // get one
    @GetMapping("/rentals/{id}")
    public ResponseEntity<?> getOneHouse(@PathVariable Long id) {
        return houseRepository.findById(id)
                .map(house -> ResponseEntity.ok(new GetRentalDTO(house)))
                .orElse(ResponseEntity.notFound().build());
    }

    // create one house
    @PostMapping("/rentals")
    // Spring convertit le JSON reçu en objet CreateHouseDTO
    // faire ça dans la couche service (le controller ne doit pas savoir ça TODO)
    public ResponseEntity<GetRentalDTO> createHouse(@RequestBody CreateRentalDTO request) {
        // Création d'une entité House
        Rentals house = new Rentals();
        // Attribution des données de la request à l'entité
        house.setName(request.getName());
        house.setSurface(request.getSurface());
        house.setPrice(request.getPrice());
        house.setPicture(request.getPicture()); //
        house.setDescription(request.getDescription());
        house.setOwnerId(request.getOwnerId());
        // Sauvegarde l'entité en base de données
        Rentals savedHouse = houseRepository.save(house);
        // Convertit l'entité sauvegardée en DTO pour la réponse
        return ResponseEntity.ok(new GetRentalDTO(savedHouse));
    }

    // @PutMapping("/rentals/{id}")
    // public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody
    // UpdateHouseDTO request) {
    // return houseRepository.findById(id)
    // .map(house -> {
    // // Met à jour les données de l'entité
    // house.setName(request.getName());
    // house.setSurface(request.getSurface());
    // house.setPrice(request.getPrice());
    // house.setPicture(request.getPicture());
    // house.setDescription(request.getDescription());
    // house.setOwnerId(request.getOwnerId());
    // // Sauvegarde les modifications
    // House updatedHouse = houseRepository.save(house);
    // // Retourne la maison mise à jour
    // return ResponseEntity.ok(new GetHouseDTO(updatedHouse));
    // })
    // .orElse(ResponseEntity.notFound().build());
    // }

    // update one house
    @PutMapping("/rentals/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody UpdateRentalDTO request) {
        // Validation des données
        request.validate();
        // Recherche de la maison
        Rentals house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No house found with id: " + id));
        // Met à jour les données de l'entité
        house.setName(request.getName());
        house.setSurface(request.getSurface());
        house.setPrice(request.getPrice());
        house.setPicture(request.getPicture());
        house.setDescription(request.getDescription());
        house.setOwnerId(request.getOwnerId());
        // Sauvegarde les modifications
        Rentals updatedHouse = houseRepository.save(house);
        return ResponseEntity.ok(new GetRentalDTO(updatedHouse));
    }

    // ------------ delete one house (dev routes)
    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long id) {
        return houseRepository.findById(id)
                .map(house -> {
                    houseRepository.delete(house);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.status(404)
                        .body("No house found with ID: " + id));
    }

    // ======== End rentals ========
}
