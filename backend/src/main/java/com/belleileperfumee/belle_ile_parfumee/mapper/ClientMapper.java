package com.belleileperfumee.belle_ile_parfumee.mapper;

import com.belleileperfumee.belle_ile_parfumee.dto.client.ClientRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.client.ClientResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Client;

public class ClientMapper {

    // Convertir RequestDTO → Entity (pour créer/sauvegarder)
    public static Client toEntity(ClientRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setLastName(dto.getLastName());
        client.setFirstName(dto.getFirstName());
        client.setPhoneNumber(dto.getPhoneNumber());
        return client;
    }

    // Convertir Entity → ResponseDTO (pour renvoyer au client)
    public static ClientResponseDTO toResponseDTO(Client client) {
        if (client == null) {
            return null;
        }

        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setEmail(client.getEmail());
        dto.setLastName(client.getLastName());
        dto.setFirstName(client.getFirstName());
        dto.setPhoneNumber(client.getPhoneNumber());
        // ✅ On ne renvoie PAS l'Account !
        return dto;
    }
}