package org.example.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    /**
     * Сообщение должно быть именно такого формата
     */
    public ProductNotFoundException() {
        super("Product not found");
    }
}
