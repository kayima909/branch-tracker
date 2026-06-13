package com.branchtracker.branchtracker.repositories;

import com.branchtracker.branchtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by their email address
    Optional<User> findByEmail(String email);

    // Check if a user with this email already exists
    boolean existsByEmail(String email);
}

