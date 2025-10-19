package com.belleileperfumee.belle_ile_parfumee.controller;

import com.belleileperfumee.belle_ile_parfumee.dto.product.ProductRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.product.ProductResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Product;
import com.belleileperfumee.belle_ile_parfumee.mapper.ProductMapper;
import com.belleileperfumee.belle_ile_parfumee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // CREATE - Créer un nouveau produit
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        // 1. Convertir DTO → Entity
        Product product = ProductMapper.toEntity(requestDTO);

        // 2. Créer le produit
        Product createdProduct = productService.createProduct(product);

        // 3. Vérifier si la création a réussi
        if (createdProduct == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Product code déjà utilisé
        }

        // 4. Convertir Entity → ResponseDTO
        ProductResponseDTO responseDTO = ProductMapper.toResponseDTO(createdProduct);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // READ - Récupérer tous les produits
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        // Convertir chaque Product → ProductResponseDTO
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // READ - Récupérer un produit par code
    @GetMapping("/{productCode}")
    public ResponseEntity<ProductResponseDTO> getProductByCode(@PathVariable String productCode) {
        Optional<Product> product = productService.getProductByCode(productCode);

        if (product.isPresent()) {
            // Convertir Entity → ResponseDTO
            ProductResponseDTO responseDTO = ProductMapper.toResponseDTO(product.get());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // UPDATE - Modifier un produit
    @PutMapping("/{productCode}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable String productCode, @RequestBody ProductRequestDTO requestDTO) {
        // Assurer que le productCode du path correspond à celui du body
        requestDTO.setProductCode(productCode);

        // 1. Convertir DTO → Entity
        Product product = ProductMapper.toEntity(requestDTO);

        // 2. Modifier le produit
        Product updatedProduct = productService.updateProduct(product);

        // 3. Vérifier si la modification a réussi
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 4. Convertir Entity → ResponseDTO
        ProductResponseDTO responseDTO = ProductMapper.toResponseDTO(updatedProduct);

        // 5. Renvoyer le DTO
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // DELETE - Supprimer un produit
    @DeleteMapping("/{productCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
        boolean deleted = productService.deleteProduct(productCode);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // BONUS - Filtrer par marque
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);

        // Convertir chaque Product → ProductResponseDTO
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    // BONUS - Filtrer par genre
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByGender(@PathVariable String gender) {
        List<Product> products = productService.getProductsByGender(gender);

        // Convertir chaque Product → ProductResponseDTO
        List<ProductResponseDTO> responseDTOs = products.stream()
                .map(ProductMapper::toResponseDTO)
                .toList();

        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }
}