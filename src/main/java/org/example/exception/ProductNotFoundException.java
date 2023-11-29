package org.example.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID uuid) {
        super("Product not found");
    }

    public ProductNotFoundException() {
        super("Product not found");
    }
}
