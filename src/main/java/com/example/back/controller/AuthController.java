package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
// dto
import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;
import com.example.back.dto.LoginRequestDTO;
import com.example.back.dto.LoginResponseDTO;
import com.example.back.model.User; // model (dans un contorller ?)
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Get user By ID
    @GetMapping("/user/{id}")
    public ResponseEntity<GetUserDTO> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @PostMapping(value = "/auth/register", produces =
    // MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<?> register(@RequestBody CreateUserDTO createUserDTO) {
    // // Vérifier si l'email existe déjà
    // if (userService.existsByEmail(createUserDTO.getEmail())) {
    // return ResponseEntity.badRequest().body("Email already exists");
    // }

    // // Créer un nouvel utilisateur avec le mot de passe hashé
    // User user = new User();
    // user.setEmail(createUserDTO.getEmail());
    // user.setName(createUserDTO.getName());
    // user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

    // User savedUser = userService.createUser(user);
    // return ResponseEntity.ok(savedUser);
    // }

    // Register new user (Json request)
    @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody CreateUserDTO createUserDTO) {
        // Vérifier si l'email existe déjà
        if (userService.existsByEmail(createUserDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        // Créer un nouvel utilisateur avec le mot de passe hashé
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setName(createUserDTO.getName());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Login (return decoded token)
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    // Get current user
    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extraire le token du header (enlever "Bearer ")
            String token = authHeader.substring(7);
            // Extraire l'email du token
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
