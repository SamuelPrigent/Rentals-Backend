package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
// dto
import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;
import com.example.back.dto.LoginRequestDTO;
import com.example.back.dto.LoginResponseDTO;
import com.example.back.service.UserService;
import com.example.back.security.JwtUtil;
import com.example.back.exception.EmailExistsException;

@RestController
@RequestMapping("/api")
@Tag(name = "Auth", description = "Points d'accès API pour l'authentification - inclut l'inscription, la connexion par token JWT et la récupération des informations utilisateur")
public class AuthController {

        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtUtil jwtUtil;

        /**
         * GET /api/user/{id}
         */
        @Operation(summary = "Get user by ID", description = "Récupère les informations d'un utilisateur par son ID")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = GetUserDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
        })
        @GetMapping("/user/{id}")
        public ResponseEntity<GetUserDTO> getUserById(
                        @Parameter(description = "ID de l'utilisateur", required = true) @PathVariable Long id) {
                return userService.getById(id)
                                .map(ResponseEntity::ok)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Utilisateur non trouvé"));
        }

        /**
         * POST /api/auth/register
         */
        @Operation(summary = "Register new user", description = "Inscription d'un nouvel utilisateur avec connexion automatique")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Inscription réussie, retourne un token JWT", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
                        @ApiResponse(responseCode = "409", description = "Email déjà utilisé"),
                        @ApiResponse(responseCode = "400", description = "Données invalides")
        })
        @PostMapping(value = "/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<LoginResponseDTO> register(
                        @Parameter(description = "Données d'inscription", required = true) @RequestBody CreateUserDTO createUserDTO) {
                // Vérifier si l'utilisateur existe déjà
                if (userService.existsByEmail(createUserDTO.getEmail())) {
                        throw new EmailExistsException();
                }
                // Créer l'utilisateur
                userService.createUser(createUserDTO);
                // Connecter automatiquement l'utilisateur
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(createUserDTO.getEmail(),
                                                createUserDTO.getPassword()));
                // get user details
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtUtil.generateToken(userDetails);
                // return token
                return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        /**
         * POST /api/auth/login
         */
        @Operation(summary = "Login user", description = "Connexion d'un utilisateur existant")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Connexion réussie, retourne un token JWT", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect")
        })
        @PostMapping("/auth/login")
        public ResponseEntity<LoginResponseDTO> login(
                        @Parameter(description = "Identifiants de connexion", required = true) @RequestBody LoginRequestDTO loginRequest) {
                // Authentification
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getPassword()));
                // get user details
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String token = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        /**
         * GET /api/auth/me
         */
        @Operation(summary = "Get current user", description = "Récupère les informations de l'utilisateur connecté")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Informations utilisateur récupérées", content = @Content(schema = @Schema(implementation = GetUserDTO.class))),
                        @ApiResponse(responseCode = "401", description = "Token invalide ou expiré"),
                        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
        })
        @GetMapping("/auth/me")
        public ResponseEntity<GetUserDTO> getCurrentUser(
                        @Parameter(description = "Token d'authentification (Bearer)", required = true) @RequestHeader("Authorization") String authHeader) {
                String token = authHeader.substring(7);
                // Extrait email du token
                String email = jwtUtil.extractClaim(token, claims -> claims.get("email", String.class));
                // Récupérer l'utilisateur depuis la base de données
                return ResponseEntity.ok(userService.getByEmail(email));
        }
}
