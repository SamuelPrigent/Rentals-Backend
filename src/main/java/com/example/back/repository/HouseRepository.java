package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.model.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    // Les méthodes basiques CRUD sont automatiquement créées par Spring Data JPA
}
