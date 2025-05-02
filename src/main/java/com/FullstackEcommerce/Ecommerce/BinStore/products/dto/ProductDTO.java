package com.FullstackEcommerce.Ecommerce.BinStore.products.dto;



import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ProductDTO {

    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;
    private String email;
    private String phoneNumber;
    private MultipartFile img; // For file upload

    // Getters and setters
    // (Generated getters and setters)
}
