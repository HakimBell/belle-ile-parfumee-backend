package com.belleileperfumee.belle_ile_parfumee.dto.client;

import lombok.Data;

@Data
public class ClientRequestDTO {
    private String email;
    private String lastName;
    private String firstName;
    private String phoneNumber;
}