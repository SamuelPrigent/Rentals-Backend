package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.back.dto.CreateMessageDTO;
import com.example.back.dto.StringResponseDTO;
import com.example.back.service.MessagesService;
import com.example.back.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * POST /api/messages
     */
    @Operation(summary = "Post a message", description = "Creates a new message and returns a response", security = @SecurityRequirement(name = "bearer-jwt"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message posted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> postMessage(
            @RequestBody CreateMessageDTO request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String userEmail = jwtUtil.extractUsername(token);
        StringResponseDTO response = messagesService.postMessage(request, userEmail);
        return ResponseEntity.ok(response);
    }
}
