package com.belleileperfumee.belle_ile_parfumee.dto.order;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String commandNumber;
    private String email; // Email du client
}