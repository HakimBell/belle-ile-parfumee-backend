package com.belleileperfumee.belle_ile_parfumee.dto.client;

import lombok.Data;

@Data
public class ClientResponseDTO {
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    // ✅ On ne renvoie PAS l'Account (qui contient le password)
}