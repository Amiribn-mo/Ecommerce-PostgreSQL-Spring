package com.FullstackEcommerce.Ecommerce.BinStore.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
