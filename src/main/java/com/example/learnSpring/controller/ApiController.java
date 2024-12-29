package com.example.learnSpring.controller;

// import com.example.learnSpring.dto.HouseByIdResponse;
import com.example.learnSpring.dto.AllHousesDTO;
import com.example.learnSpring.dto.OneHouseDTO;
import com.example.learnSpring.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// REST API (détecté par spring)
@RestController
@RequestMapping("/api")
public class ApiController {

    // --- Database tables ---
    @Autowired
    private HouseRepository houseRepository;

    // hello
    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello from Rentals API !");
    }

    // get all
    @GetMapping("/rentals")
    public AllHousesDTO getAllHouses() {
        return new AllHousesDTO(houseRepository.findAll());
    }

    // get by id
    @GetMapping("/rentals/{id}")
    public ResponseEntity<?> getOneHouse(@PathVariable Long id) {
        return houseRepository.findById(id)
                // .map(house -> ResponseEntity.ok(new HouseByIdResponse(house)))
                .map(house -> ResponseEntity.ok(new OneHouseDTO(house)))
                .orElse(ResponseEntity.notFound().build());
    }
}
