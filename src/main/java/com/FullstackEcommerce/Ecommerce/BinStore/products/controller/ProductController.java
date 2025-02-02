package com.FullstackEcommerce.Ecommerce.BinStore.products.controller;

import com.FullstackEcommerce.Ecommerce.BinStore.exceptions.ResourceNotFoundException;
import com.FullstackEcommerce.Ecommerce.BinStore.products.service.ProductService;
import com.FullstackEcommerce.Ecommerce.BinStore.products.model.Product;

import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5174")  // Frontend URL
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();  // Get the list of products
            return ResponseEntity.ok(products);  // Return the list of products
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch products. Error: " + e.getMessage());
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);  // Call the service to get the product by ID
            return ResponseEntity.ok(product);  // Return the found product
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Return 404 for not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch product. Error: " + e.getMessage());  // Handle other exceptions
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestParam String name,
                                        @RequestParam double price,
                                        @RequestParam int quantity,
                                        @RequestParam String category,
                                        @RequestParam String description,
                                        @RequestParam String email,
                                        @RequestParam String phoneNumber,
                                        @RequestParam String imgUrl) {
        try {
            Product savedProduct = productService.saveProduct(name, price, quantity, category,
                    description, email, phoneNumber, imgUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product. Error: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product); // Return updated product
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete product. Error: " + e.getMessage());
        }
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String query) {
        try {
            List<Product> products = productService.searchProductsByName(query);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch search results. Error: " + e.getMessage());
        }
    }



    @GetMapping("/category/{category}")
    public ResponseEntity<?> getRecommendedProducts(@PathVariable String category) {
        try {
            List<Product> recommendedProducts = productService.getProductsByCategory(category);
            return ResponseEntity.ok(recommendedProducts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch recommended products. Error: " + e.getMessage());
        }
    }
}
