package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.back.model.User; // model
import com.example.back.repository.UserRepository; // repo
// dto
import com.example.back.dto.CreateUserDTO;
import com.example.back.dto.GetUserDTO;
// spring security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // hash password

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Create user
    public GetUserDTO createUser(CreateUserDTO createUserDTO) {
        createUserDTO.validate(); // Validation des données
        // Vérification si l'email existe déjà
        if (existsByEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }
        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setName(createUserDTO.getName());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));

        User savedUser = userRepository.save(user);
        return new GetUserDTO(savedUser);
    }

    public Optional<GetUserDTO> getById(Long id) {
        return userRepository.findById(id)
                .map(user -> new GetUserDTO(user));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
