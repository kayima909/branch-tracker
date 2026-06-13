package com.branchtracker.branchtracker.repositories;

import com.branchtracker.branchtracker.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Find a client by their national ID
    Optional<Client> findByNationalId(String nationalId);

    // Find all clients belonging to a specific branch
    List<Client> findByBranchId(Long branchId);

    // Check if a client with this national ID already exists
    boolean existsByNationalId(String nationalId);

    // Count how many clients a branch has
    long countByBranchId(Long branchId);
}

