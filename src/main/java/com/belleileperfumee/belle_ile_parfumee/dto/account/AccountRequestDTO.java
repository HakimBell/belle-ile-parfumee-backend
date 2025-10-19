package com.belleileperfumee.belle_ile_parfumee.dto.account;

import lombok.Data;

@Data
public class AccountRequestDTO {
    private String email;
    private String password;
    private String role;
}