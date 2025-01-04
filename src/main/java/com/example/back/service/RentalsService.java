package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import com.example.back.exception.ResourceNotFoundException;
// model
import com.example.back.model.Rentals;
// repo
import com.example.back.repository.RentalRepository;
// dto
import com.example.back.dto.GetAllRentalDTO;
import com.example.back.dto.GetRentalDTO;
import com.example.back.dto.CreateRentalDTO;
import com.example.back.dto.UpdateRentalDTO;

@Service
public class RentalsService {

    @Autowired // Database table
    private RentalRepository rentalRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    // get all
    public GetAllRentalDTO getAllRentals() {
        List<Rentals> rentals = rentalRepository.findAll();
        return new GetAllRentalDTO(rentals);
    }

    // get one rental
    public Optional<GetRentalDTO> getOneRental(Long id) {
        return rentalRepository.findById(id)
                .map(rental -> new GetRentalDTO(rental));
    }

    // create rental
    public GetRentalDTO createRental(CreateRentalDTO request) throws IOException {
        // Création d'une entité rental
        Rentals rental = new Rentals();
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
        rental.setOwnerId(request.getOwnerId());
        // Sauvegarde l'entité en base de données
        Rentals savedRental = rentalRepository.save(rental);
        return new GetRentalDTO(savedRental);
    }

    // update rental
    public GetRentalDTO updateRental(Long id, UpdateRentalDTO request) {
        // Validation des données
        request.validate();
        // Search Rental
        Rentals rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rental found with id: " + id));
        // Updated the specific rental
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setPicture(request.getPicture());
        rental.setDescription(request.getDescription());
        // Sauvegarde les modifications
        Rentals updatedRental = rentalRepository.save(rental);
        return new GetRentalDTO(updatedRental);
    }
}
