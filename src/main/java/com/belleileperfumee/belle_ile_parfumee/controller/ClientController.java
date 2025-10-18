package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.entity.Client;
import com.belleileperfumee.belle_ile_parfumee.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // CREATE - Créer un nouveau client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.createClient(client);
        if (createdClient != null) {
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT); // Email déjà utilisé
    }

    // READ - Récupérer tous les clients
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    // READ - Récupérer un client par email
    @GetMapping("/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.getClientByEmail(email);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // READ - Récupérer un client par téléphone
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<Client> getClientByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Client> client = clientService.getClientByPhoneNumber(phoneNumber);
        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE - Modifier un client
    @PutMapping("/{email}")  // ✅ AJOUTER /{email}
    public ResponseEntity<Client> updateClient(@PathVariable String email, @RequestBody Client client) {
        // Assurer que l'email du path correspond à celui du body
        client.setEmail(email);

        Client updatedClient = clientService.updateClient(client);
        if (updatedClient != null) {
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - Supprimer un client
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteClient(@PathVariable String email) {
        boolean deleted = clientService.deleteClient(email);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
