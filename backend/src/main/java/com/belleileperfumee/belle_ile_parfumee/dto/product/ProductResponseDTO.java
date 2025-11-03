package com.belleileperfumee.belle_ile_parfumee.dto.product;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductResponseDTO {
    private String productCode;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private LocalDate createdAt;
    private String concentrationType;
    private String gender;
    private Integer size;
}