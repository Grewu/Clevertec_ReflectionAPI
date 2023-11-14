package org.example.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    /**
     * Сообщение должно быть именно такого формата
     */
    public ProductNotFoundException(UUID uuid) {
        super("Product not found");
    }
    public ProductNotFoundException() {
        super("Product not found");
    }
    public ProductNotFoundException(UUID uuid) {
        super("Product not found with uuid " + uuid);
    }
}
