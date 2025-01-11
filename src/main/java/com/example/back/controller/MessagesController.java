package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.back.dto.CreateMessageDTO;
import com.example.back.dto.StringResponseDTO;
import com.example.back.service.MessagesService;
import com.example.back.security.JwtUtil;

@RestController
@RequestMapping("/api")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    @Autowired
    private JwtUtil jwtUtil;

    // Post message (Json request)
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
