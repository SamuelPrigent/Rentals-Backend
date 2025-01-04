package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.back.model.Message;
import com.example.back.model.Rentals;
import com.example.back.model.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Trouve tous les messages d'un rental spécifique
    List<Message> findByRental(Rentals rental);

    // Trouve tous les messages d'un utilisateur spécifique
    List<Message> findByUser(User user);

    // All msg d'un rental : par date de création (récent -> ancien)
    List<Message> findByRentalOrderByCreatedAtDesc(Rentals rental);
}
