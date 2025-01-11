package com.example.back.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.back.model.Message;
import com.example.back.model.Rentals;
import com.example.back.model.User;
import com.example.back.repository.MessageRepository;
import com.example.back.repository.RentalRepository;
import com.example.back.dto.CreateMessageDTO;
import com.example.back.dto.StringResponseDTO;
import com.example.back.exception.ResourceNotFoundException;
import com.example.back.exception.UnauthorizedRentalAccessException;

@Service
public class MessagesService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    public StringResponseDTO postMessage(CreateMessageDTO request, String userEmail) {
        // Validation des données
        request.validate();

        // Get token owner user
        User tokenOwner = userService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if userId in request = token owner
        if (!request.getUserId().equals(tokenOwner.getId())) {
            throw new UnauthorizedRentalAccessException(
                    "The user associated with this token is not authorized to send messages on behalf of another user");
        }

        // Récupération du rental
        Rentals rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental non trouvé"));

        // Création du message
        Message message = new Message();
        message.setContent(request.getMessage());
        message.setUser(tokenOwner); // On utilise tokenOwner puisqu'on a vérifié que c'est le bon user
        message.setRental(rental);
        message.setCreatedAt(LocalDateTime.now());

        // Sauvegarde du message
        messageRepository.save(message);
        return new StringResponseDTO("Message send with success");
    }
}
