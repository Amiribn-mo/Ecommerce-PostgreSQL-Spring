package com.FullstackEcommerce.Ecommerce.BinStore.products.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
    private String category;
    private String description;
    private String email;
    private String phoneNumber;

    private String imgUrl;  // Store image URL or path

    public Product() {}

    public Product(String name, double price, int quantity, String category, String description, String email, String phoneNumber, String imgUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imgUrl = imgUrl;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // ** Additional methods (added functionality) **

    // Method to decrease the quantity when a product is added to a cart
    public boolean reduceStock(int quantity) {
        if (this.quantity >= quantity) {
            this.quantity -= quantity;
            return true;  // Stock successfully reduced
        }
        return false;  // Not enough stock
    }

    // Method to increase the quantity when the product stock is restocked
    public void increaseStock(int quantity) {
        this.quantity += quantity;
    }

    // Method to check if there is enough stock for a given quantity
    public boolean hasEnoughStock(int quantity) {
        return this.quantity >= quantity;
    }

    public int getStockQuantity() {
        return this.quantity;
    }

    public void setStockQuantity(int updatedQuantity) {

    }
}
