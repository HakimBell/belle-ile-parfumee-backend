package com.belleileperfumee.belle_ile_parfumee.dto.account;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String email;
    private String role;
    private String token; // ✅ Le token JWT
}