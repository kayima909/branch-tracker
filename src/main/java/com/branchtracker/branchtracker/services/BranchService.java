package com.branchtracker.branchtracker.services;

import com.branchtracker.branchtracker.entity.Branch;
import com.branchtracker.branchtracker.repositories.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    // Create a new branch
    public Branch createBranch(Branch branch) {
        if (branchRepository.existsByName(branch.getName())) {
            throw new RuntimeException("Branch already exists: " + branch.getName());
        }
        return branchRepository.save(branch);
    }

    // Get all branches
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    // Get all active branches
    public List<Branch> getActiveBranches() {
        return branchRepository.findByActiveTrue();
    }

    // Get a branch by ID
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    // Update a branch
    public Branch updateBranch(Long id, Branch updatedBranch) {
        Branch existing = getBranchById(id);
        existing.setName(updatedBranch.getName());
        existing.setLocation(updatedBranch.getLocation());
        existing.setRegion(updatedBranch.getRegion());
        existing.setContactPhone(updatedBranch.getContactPhone());
        existing.setContactEmail(updatedBranch.getContactEmail());
        return branchRepository.save(existing);
    }

    // Deactivate a branch (soft delete)
    public void deactivateBranch(Long id) {
        Branch branch = getBranchById(id);
        branch.setActive(false);
        branchRepository.save(branch);
    }
}

