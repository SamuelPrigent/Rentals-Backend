package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;
import com.example.back.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // Register new user (Json request)
    @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserDTO> createUser(@RequestBody CreateUserDTO request) {
        GetUserDTO createdUser = userService.create(request);
        return ResponseEntity.ok(createdUser);
    }

    // Get user By ID
    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
