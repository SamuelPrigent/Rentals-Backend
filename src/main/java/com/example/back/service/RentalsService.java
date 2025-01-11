package com.example.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.io.IOException;
import com.example.back.exception.ResourceNotFoundException;
import com.example.back.exception.UnauthorizedRentalAccessException;
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
import com.example.back.dto.StringResponseDTO;

@Service
public class RentalsService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserService userService;

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
    public StringResponseDTO create(CreateRentalDTO request) throws IOException {
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
        rental.setDescription(request.getDescription());
        rental.setOwner(owner); // owner_id checked
        rental.setPicture(pictureUrl); // cloudinary
        // Sauvegarde l'entité en base de données
        rentalRepository.save(rental);
        // Reponse
        return new StringResponseDTO("Rental created !");
    }

    // Update rental
    public StringResponseDTO update(Long id, UpdateRentalDTO request, String userEmail) {
        // Validation des données
        request.validate();
        // Search Rental
        Rentals rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No rental found with id: " + id));
        // Get token owner user
        User owner = userService.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Check if rental have an ownerId
        if (rental.getOwner() == null) {
            throw new UnauthorizedRentalAccessException("This rental has no owner defined");
        }
        // Check if owner Id of the rental is the token owner
        if (!rental.getOwner().getId().equals(owner.getId())) {
            throw new UnauthorizedRentalAccessException(
                    "The user associated with this token is not authorized to modify this rental");
        }
        // Update the specific rental
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());

        // Sauvegarde les modifications
        rentalRepository.save(rental);

        return new StringResponseDTO("Rental updated successfully");
    }
}
