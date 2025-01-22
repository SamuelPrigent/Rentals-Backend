package com.example.back.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseController {

    @Hidden // Hide from OpenAPI / Swagger
    @GetMapping("/")
    public ResponseEntity<String> getResponse() {
        return ResponseEntity.ok("Rentals API");
    }
}
