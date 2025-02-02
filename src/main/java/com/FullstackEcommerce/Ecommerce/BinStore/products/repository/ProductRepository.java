package com.FullstackEcommerce.Ecommerce.BinStore.products.repository;

import com.FullstackEcommerce.Ecommerce.BinStore.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    // Search products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory(String category);  // Fetch products by category

}