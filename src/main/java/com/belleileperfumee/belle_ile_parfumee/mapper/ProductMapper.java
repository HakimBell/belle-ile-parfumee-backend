package com.belleileperfumee.belle_ile_parfumee.mapper;

import com.belleileperfumee.belle_ile_parfumee.dto.product.ProductRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.product.ProductResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Product;

public class ProductMapper {

    // Convertir RequestDTO → Entity (pour créer/sauvegarder)
    public static Product toEntity(ProductRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImageUrl());
        product.setConcentrationType(dto.getConcentrationType());
        product.setGender(dto.getGender());
        product.setSize(dto.getSize());
        // createdAt sera généré automatiquement par @PrePersist

        return product;
    }

    // Convertir Entity → ResponseDTO (pour renvoyer au client)
    public static ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductCode(product.getProductCode());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setConcentrationType(product.getConcentrationType());
        dto.setGender(product.getGender());
        dto.setSize(product.getSize());

        return dto;
    }
}