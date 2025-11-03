package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.dto.client.ClientRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.client.ClientResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Client;
import com.belleileperfumee.belle_ile_parfumee.mapper.ClientMapper;
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
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO requestDTO) {
        // 1. Convertir DTO → Entity
        Client client = ClientMapper.toEntity(requestDTO);

        // 2. Créer le client
        Client createdClient = clientService.createClient(client);

        // 3. Vérifier si la création a réussi
        if (createdClient == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Email déjà utilisé
        }

        // 4. Convertir Entity → ResponseDTO (SANS l'Account)
        ClientResponseDTO responseDTO = ClientMapper.toResponseDTO(createdClient);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // READ - Récupérer tous les clients
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<Client> clients = clientService.getAllClients();

        // Convertir chaque Client → ClientResponseDTO
        List<ClientResponseDTO> responseDTOs = clients.stream()
                .map(ClientMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // READ - Récupérer un client par email
    @GetMapping("/{email}")
    public ResponseEntity<ClientResponseDTO> getClientByEmail(@PathVariable String email) {
        Optional<Client> client = clientService.getClientByEmail(email);

        if (client.isPresent()) {
            // Convertir Entity → ResponseDTO (SANS l'Account)
            ClientResponseDTO responseDTO = ClientMapper.toResponseDTO(client.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // READ - Récupérer un client par téléphone
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<ClientResponseDTO> getClientByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Client> client = clientService.getClientByPhoneNumber(phoneNumber);

        if (client.isPresent()) {
            // Convertir Entity → ResponseDTO (SANS l'Account)
            ClientResponseDTO responseDTO = ClientMapper.toResponseDTO(client.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // UPDATE - Modifier un client
    @PutMapping("/{email}")
    public ResponseEntity<ClientResponseDTO> updateClient(@PathVariable String email, @RequestBody ClientRequestDTO requestDTO) {
        // Assurer que l'email du path correspond à celui du body
        requestDTO.setEmail(email);

        // 1. Convertir DTO → Entity
        Client client = ClientMapper.toEntity(requestDTO);

        // 2. Modifier le client
        Client updatedClient = clientService.updateClient(client);

        // 3. Vérifier si la modification a réussi
        if (updatedClient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 4. Convertir Entity → ResponseDTO (SANS l'Account)
        ClientResponseDTO responseDTO = ClientMapper.toResponseDTO(updatedClient);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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
