package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.back.exception.ResourceNotFoundException;
// model
import com.example.back.model.Message;
import com.example.back.model.User;
import com.example.back.model.Rentals;
// repo
import com.example.back.repository.MessageRepository;
import com.example.back.repository.UserRepository;
import com.example.back.repository.RentalRepository;
// dto
import com.example.back.dto.CreateMessageDTO;
import com.example.back.dto.MessageResponseDTO;

@Service
public class MessagesService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public MessageResponseDTO postMessage(CreateMessageDTO request) {
        // Validation des données
        request.validate();
        // Récupération de l'utilisateur
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        // Récupération du rental
        Rentals rental = rentalRepository.findById(request.getRentalId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental non trouvé"));
        // Création du message
        Message message = new Message();
        message.setContent(request.getMessage());
        message.setUser(user);
        message.setRental(rental);
        // Sauvegarde du message
        messageRepository.save(message);
        return new MessageResponseDTO("Message send with success");
    }
}
