package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import com.example.back.exception.ResourceNotFoundException;
// model
import com.example.back.model.Rentals;
import com.example.back.model.User;
// repo
import com.example.back.repository.RentalRepository;
import com.example.back.repository.UserRepository;
// dto
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.UpdateRentalDTO;

@Service
public class RentalsService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // get all rentals
    public GetAllRentalDTO getAll() {
        List<Rentals> rentals = rentalRepository.findAll();
        List<GetRentalDTO> rentalDTOs = rentals.stream()
                .map(rental -> new GetRentalDTO(rental))
                .toList();
        return new GetAllRentalDTO(rentalDTOs);
    }

    // get one rental with id
    public Optional<GetRentalDTO> getById(Long id) {
        return rentalRepository.findById(id)
                .map(rental -> new GetRentalDTO(rental));
    }

    // create rental
    public GetRentalDTO create(CreateRentalDTO request) throws IOException {
        // Création d'une entité rental
        Rentals rental = new Rentals();

        // Récupération de l'utilisateur propriétaire
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getOwnerId()));

        // Upload de l'image sur Cloudinary
        String pictureUrl = null;
        if (request.getPicture() != null && !request.getPicture().isEmpty()) {
            pictureUrl = cloudinaryService.uploadFile(request.getPicture());
        }

        // Attribution des données de la request à l'entité
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(pictureUrl);
        rental.setDescription(request.getDescription());
        rental.setOwner(owner);

        // Sauvegarde l'entité en base de données
        Rentals savedRental = rentalRepository.save(rental);
        return new GetRentalDTO(savedRental);
    }

    // Update rental
    public GetRentalDTO update(Long id, UpdateRentalDTO request) {
        // Validation des données
        request.validate();
        // Search Rental
        Rentals rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rental found with id: " + id));
        // Updated the specific rental
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        // Sauvegarde les modifications
        Rentals updatedRental = rentalRepository.save(rental);
        return new GetRentalDTO(updatedRental);
    }
}
