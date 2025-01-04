package com.example.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.example.back.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode personnalisée pour trouver un utilisateur par email
    Optional<User> findByEmail(String email);
}
