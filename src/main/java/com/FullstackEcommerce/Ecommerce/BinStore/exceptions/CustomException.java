package com.FullstackEcommerce.Ecommerce.BinStore.exceptions;



public abstract class CustomException extends RuntimeException {
    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

