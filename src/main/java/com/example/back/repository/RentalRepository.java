package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.model.Rentals;

@Repository
public interface RentalRepository extends JpaRepository<Rentals, Long> {
    // Les méthodes basiques CRUD sont automatiquement créées par Spring Data JPA
}
