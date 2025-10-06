package com.belleileperfumee.belle_ile_parfumee.repository;

import com.belleileperfumee.belle_ile_parfumee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductCode(String productCode);

    List<Product> findByBrand(String brand);

    List<Product> findByGender(String gender);

    List<Product> findByConcentrationType(String concentrationType);

    List<Product> findByStockGreaterThan(Integer stock);
}
