package org.example.exception;

import java.util.UUID;

public class ProductDeleteException extends RuntimeException {
    public ProductDeleteException(UUID uuid) {
        super("Product can't delete  because " + uuid);
    }
}
