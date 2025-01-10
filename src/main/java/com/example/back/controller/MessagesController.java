package com.example.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.dto.CreateMessageDTO;
import com.example.back.dto.StringResponseDTO;
import com.example.back.service.MessagesService;

@RestController
@RequestMapping("/api")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    // Post message (Json request)
    @PostMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StringResponseDTO> postMessage(@RequestBody CreateMessageDTO request) {
        StringResponseDTO response = messagesService.postMessage(request);
        return ResponseEntity.ok(response);
    }
}
