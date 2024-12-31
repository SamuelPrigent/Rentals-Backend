package com.example.learnSpring.controller;

import com.example.learnSpring.dto.GetAllHousesDTO;
import com.example.learnSpring.dto.GetHouseDTO;
import com.example.learnSpring.dto.CreateHouseDTO;
import com.example.learnSpring.dto.UpdateHouseDTO;
import com.example.learnSpring.model.House;
import com.example.learnSpring.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.learnSpring.exception.ResourceNotFoundException;

// REST API (détecté par spring)
@RestController
@RequestMapping("/api")
public class ApiController {

    // --- Database tables ---
    @Autowired
    private HouseRepository houseRepository;

    // ======== Hello (check api) ========
    @GetMapping("/")
    public ResponseEntity<String> getResponse() {
        return ResponseEntity.ok("Rentals API");
    }

    // ======== Houses ========
    // get all
    @GetMapping("/rentals")
    public GetAllHousesDTO getAllHouses() {
        return new GetAllHousesDTO(houseRepository.findAll());
    }

    // get one
    @GetMapping("/rentals/{id}")
    public ResponseEntity<?> getOneHouse(@PathVariable Long id) {
        return houseRepository.findById(id)
                .map(house -> ResponseEntity.ok(new GetHouseDTO(house)))
                .orElse(ResponseEntity.notFound().build());
    }

    // create one house
    @PostMapping("/rentals")
    // Spring convertit le JSON reçu en objet CreateHouseDTO
    public ResponseEntity<GetHouseDTO> createHouse(@RequestBody CreateHouseDTO request) {
        // Création d'une entité House
        House house = new House();
        // Attribution des données de la request à l'entité
        house.setName(request.getName());
        house.setSurface(request.getSurface());
        house.setPrice(request.getPrice());
        house.setPicture(request.getPicture());
        house.setDescription(request.getDescription());
        house.setOwnerId(request.getOwnerId());
        // Sauvegarde l'entité en base de données
        House savedHouse = houseRepository.save(house);
        // Convertit l'entité sauvegardée en DTO pour la réponse
        return ResponseEntity.ok(new GetHouseDTO(savedHouse));
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
    public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody UpdateHouseDTO request) {
        // Validation des données
        request.validate();
        // Recherche de la maison
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No house found with id: " + id));
        // Met à jour les données de l'entité
        house.setName(request.getName());
        house.setSurface(request.getSurface());
        house.setPrice(request.getPrice());
        house.setPicture(request.getPicture());
        house.setDescription(request.getDescription());
        house.setOwnerId(request.getOwnerId());
        // Sauvegarde les modifications
        House updatedHouse = houseRepository.save(house);
        return ResponseEntity.ok(new GetHouseDTO(updatedHouse));
    }

    // delete one house
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
