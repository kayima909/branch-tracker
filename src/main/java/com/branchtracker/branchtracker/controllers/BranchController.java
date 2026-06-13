package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.entity.Branch;
import com.branchtracker.branchtracker.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    // Create a new branch — HQ_ADMIN only
    @PostMapping
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.createBranch(branch));
    }

    // Get all branches — HQ_ADMIN only
    @GetMapping
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    // Get all active branches — HQ_ADMIN only
    @GetMapping("/active")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<Branch>> getActiveBranches() {
        return ResponseEntity.ok(branchService.getActiveBranches());
    }

    // Get a branch by ID — HQ_ADMIN and BRANCH_MANAGER
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    // Update a branch — HQ_ADMIN only
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id,
                                               @RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.updateBranch(id, branch));
    }

    // Deactivate a branch — HQ_ADMIN only
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<?> deactivateBranch(@PathVariable Long id) {
        branchService.deactivateBranch(id);
        return ResponseEntity.ok(java.util.Map.of("message", "Branch deactivated successfully"));
    }
}

