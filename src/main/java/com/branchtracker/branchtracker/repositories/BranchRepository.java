package com.branchtracker.branchtracker.repositories;

import com.branchtracker.branchtracker.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    // Find a branch by its name
    Optional<Branch> findByName(String name);

    // Find all active branches
    List<Branch> findByActiveTrue();

    // Check if a branch with this name already exists
    boolean existsByName(String name);
}

