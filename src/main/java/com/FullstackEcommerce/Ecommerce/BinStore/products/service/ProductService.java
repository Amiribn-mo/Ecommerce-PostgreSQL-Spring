package com.FullstackEcommerce.Ecommerce.BinStore.products.service;

import com.FullstackEcommerce.Ecommerce.BinStore.exceptions.ResourceNotFoundException;
import com.FullstackEcommerce.Ecommerce.BinStore.products.model.Product;
import com.FullstackEcommerce.Ecommerce.BinStore.products.repository.ProductRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public List<Product> getAllProducts() {
        return productRepository.findAll();  // Fetch all products from DB
    }


    public Product saveProduct(String name, double price, int quantity, String category,
                               String description, String email, String phoneNumber,
                               String imgUrl) throws ValidationException {

        if (name == null || name.isEmpty() || price <= 0) {
            throw new ValidationException("Invalid product data: name or price cannot be null or negative");
        }

        Product newProduct = new Product(name, price, quantity, category, description, email, phoneNumber, imgUrl);
        return productRepository.save(newProduct);  // Save the new product
    }

    // Delete a product by ID
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);  // Delete the product by ID
    }

    // Search products by name
    public List<Product> searchProductsByName(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);  // Search for products by name
    }

    // Get a product by its ID
    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        // If the product is not found, throw ResourceNotFoundException
        return product.orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }

    // Update a product
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    product.setQuantity(updatedProduct.getQuantity());
                    product.setCategory(updatedProduct.getCategory());
                    product.setDescription(updatedProduct.getDescription());
                    product.setEmail(updatedProduct.getEmail());
                    product.setPhoneNumber(updatedProduct.getPhoneNumber());
                    product.setImgUrl(updatedProduct.getImgUrl());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found."));
    }

    // Fetch products by category for recommendations
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);  // Fetch products by category
    }

    // Update stock quantity
    public Product updateStockQuantity(Long productId, int quantityChange) {
        Product product = getProductById(productId);

        // Ensure stock quantity doesn't go negative
        int updatedQuantity = product.getStockQuantity() - quantityChange;
        if (updatedQuantity < 0) {
            throw new ResourceNotFoundException("Not enough stock for product with ID " + productId);
        }

        product.setStockQuantity(updatedQuantity);
        return productRepository.save(product);
    }

    // Restore stock quantity (used when removing products from the cart)
    public Product restoreStockQuantity(Long productId, int quantityChange) {
        Product product = getProductById(productId);

        int updatedQuantity = product.getStockQuantity() + quantityChange;
        product.setStockQuantity(updatedQuantity);
        return productRepository.save(product);
    }
}
