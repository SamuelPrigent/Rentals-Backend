package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
// dto
import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;
import com.example.back.dto.LoginRequestDTO;
import com.example.back.dto.LoginResponseDTO;
import com.example.back.service.UserService; // service
import com.example.back.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Get user By ID
    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create user (hashage du password)
    @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody CreateUserDTO createUserDTO) {
        // Service pour check si utilisateur existe déjà
        if (userService.existsByEmail(createUserDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        // Création de l'utilisateur avec le service
        GetUserDTO savedUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(savedUser);
    }

    // Login (return token)
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // authentification
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            // get user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    // Get user info with token
    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extrait le token du header
            String token = authHeader.substring(7);
            // Extrait email du token
            String email = jwtUtil.extractClaim(token, claims -> claims.get("email", String.class));
            // Récupérer l'utilisateur depuis la base de données
            return userService.findByEmail(email)
                    .map(user -> ResponseEntity.ok(new GetUserDTO(user)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}
