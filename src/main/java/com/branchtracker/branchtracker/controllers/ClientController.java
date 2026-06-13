package com.branchtracker.branchtracker.controllers;

import com.branchtracker.branchtracker.entity.Client;
import com.branchtracker.branchtracker.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    // Create a new client — BRANCH_MANAGER and LOAN_OFFICER
    @PostMapping
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.createClient(client));
    }

    // Get all clients — HQ_ADMIN only
    @GetMapping
    @PreAuthorize("hasRole('HQ_ADMIN')")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    // Get clients by branch — HQ_ADMIN and BRANCH_MANAGER
    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<List<Client>> getClientsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(clientService.getClientsByBranch(branchId));
    }

    // Get a client by ID — all roles
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER', 'LOAN_OFFICER')")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    // Update a client — BRANCH_MANAGER and above
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
                                               @RequestBody Client client) {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }

    // Deactivate a client — BRANCH_MANAGER and above
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<?> deactivateClient(@PathVariable Long id) {
        clientService.deactivateClient(id);
        return ResponseEntity.ok(Map.of("message", "Client deactivated successfully"));
    }

    // Count clients per branch
    @GetMapping("/branch/{branchId}/count")
    @PreAuthorize("hasAnyRole('HQ_ADMIN', 'BRANCH_MANAGER')")
    public ResponseEntity<?> countClientsByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(Map.of("count", clientService.countClientsByBranch(branchId)));
    }
}

