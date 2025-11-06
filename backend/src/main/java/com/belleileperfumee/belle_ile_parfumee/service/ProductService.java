package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Product;
import com.belleileperfumee.belle_ile_parfumee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE - Créer un nouveau produit avec génération automatique du code
    public Product createProduct(Product product) {
        // Générer un code produit automatique si non fourni
        if (product.getProductCode() == null || product.getProductCode().isEmpty()) {
            product.setProductCode(generateProductCode());
        }
        return productRepository.save(product);
    }

    // Méthode privée pour générer un code produit unique
    private String generateProductCode() {
        String prefix = "PROD-";
        long timestamp = System.currentTimeMillis();
        String code = prefix + timestamp;

        // Vérifier que le code n'existe pas déjà (très peu probable)
        while (productRepository.existsById(code)) {
            timestamp++;
            code = prefix + timestamp;
        }

        return code;
    }

    // READ - Récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ - Récupérer un produit par son code
    public Optional<Product> getProductByCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    @Transactional
    public Product updateProduct(Product updatedProduct) {
        // Cherche le produit existant par son code
        return productRepository.findById(updatedProduct.getProductCode())
                .map(existingProduct -> {
                    // Mettre à jour uniquement les champs fournis
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setDescription(updatedProduct.getDescription());
                    existingProduct.setBrand(updatedProduct.getBrand());
                    existingProduct.setGender(updatedProduct.getGender());
                    existingProduct.setImageUrl(updatedProduct.getImageUrl());
                    return productRepository.save(existingProduct);
                })
                .orElse(null); // Si le produit n'existe pas, retourne null
    }

    // DELETE - Supprimer un produit
    public boolean deleteProduct(String productCode) {
        if (productRepository.existsById(productCode)) {
            productRepository.deleteById(productCode);
            return true;
        }
        return false; // Produit non trouvé
    }

    // BONUS - Rechercher par marque
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // BONUS - Rechercher par genre
    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGender(gender);
    }
}