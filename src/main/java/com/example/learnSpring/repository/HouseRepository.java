package com.example.learnSpring.repository;

import com.example.learnSpring.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    // Les méthodes basiques CRUD sont automatiquement créées par Spring Data JPA
}
