package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.back.model.User;
import com.example.back.repository.UserRepository;
import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create user
    public GetUserDTO create(CreateUserDTO request) {
        // Validation des données
        request.validate();
        // Vérification si l'email existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }
        // Création d'une entité user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword()); // TO DO: Hash password before saving
        // Sauvegarde l'entité en base de données
        User savedUser = userRepository.save(user);
        return new GetUserDTO(savedUser);
    }

    // Get user by id
    public Optional<GetUserDTO> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> new GetUserDTO(user));
    }
}
