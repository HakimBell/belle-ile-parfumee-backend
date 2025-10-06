package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Client;
import com.belleileperfumee.belle_ile_parfumee.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Créer un nouveau client
    public Client createClient(Client client) {
        // Vérifier si l'email existe déjà
        if (clientRepository.existsById(client.getEmail())) {
            return null; // Email déjà utilisé
        }
        return clientRepository.save(client);
    }

    // Récupérer tous les clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Récupérer un client par email
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    // Récupérer un client par numéro de téléphone
    public Optional<Client> getClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);
    }

    // Modifier un client
    public Client updateClient(Client updatedClient) {
        if (clientRepository.existsById(updatedClient.getEmail())) {
            return clientRepository.save(updatedClient);
        }
        return null; // Client non trouvé
    }

    // Supprimer un client
    public boolean deleteClient(String email) {
        if (clientRepository.existsById(email)) {
            clientRepository.deleteById(email);
            return true;
        }
        return false; // Client non trouvé
    }
}
