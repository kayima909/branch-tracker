package com.branchtracker.branchtracker.services;

import com.branchtracker.branchtracker.entity.Client;
import com.branchtracker.branchtracker.repositories.BranchRepository;
import com.branchtracker.branchtracker.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final BranchRepository branchRepository;

    // Create a new client
    public Client createClient(Client client) {
        if (client.getNationalId() != null &&
                clientRepository.existsByNationalId(client.getNationalId())) {
            throw new RuntimeException("Client already exists with National ID: "
                    + client.getNationalId());
        }
        return clientRepository.save(client);
    }

    // Get all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Get all clients for a specific branch
    public List<Client> getClientsByBranch(Long branchId) {
        return clientRepository.findByBranchId(branchId);
    }

    // Get a client by ID
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    // Update a client
    public Client updateClient(Long id, Client updatedClient) {
        Client existing = getClientById(id);
        existing.setFullName(updatedClient.getFullName());
        existing.setPhone(updatedClient.getPhone());
        existing.setEmail(updatedClient.getEmail());
        existing.setAddress(updatedClient.getAddress());
        existing.setDateOfBirth(updatedClient.getDateOfBirth());
        return clientRepository.save(existing);
    }

    // Deactivate a client (soft delete)
    public void deactivateClient(Long id) {
        Client client = getClientById(id);
        client.setActive(false);
        clientRepository.save(client);
    }

    // Count clients per branch
    public long countClientsByBranch(Long branchId) {
        return clientRepository.countByBranchId(branchId);
    }
}
