package com.belleileperfumee.belle_ile_parfumee.dto;

import lombok.Data;

@Data
public class AccountResponseDTO {
    private String email;
    private String role;
    // ✅ PAS de password ici !
}