package com.belleileperfumee.belle_ile_parfumee.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @Column(name = "product_code", length = 50, nullable = false, updatable = false)
    private String productCode;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String brand;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "concentration_type", length = 20, nullable = false)
    private String concentrationType;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer size;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        if (this.productCode == null || this.productCode.isBlank()) {
            this.productCode = "PROD-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        }
    }
}
