package com.belleileperfumee.belle_ile_parfumee.service;

import com.belleileperfumee.belle_ile_parfumee.entity.Product;
import com.belleileperfumee.belle_ile_parfumee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CREATE - Créer un nouveau produit
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // READ - Récupérer tous les produits
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // READ - Récupérer un produit par son code
    public Optional<Product> getProductByCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    // UPDATE - Modifier un produit existant
    public Product updateProduct(Product updatedProduct) {
        if (productRepository.existsById(updatedProduct.getProductCode())) {
            return productRepository.save(updatedProduct);
        }
        return null; // Produit non trouvé
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