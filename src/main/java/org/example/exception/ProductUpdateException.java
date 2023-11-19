package org.example.exception;

import java.util.UUID;

public class ProductUpdateException extends RuntimeException {
    public ProductUpdateException(UUID uuid) {
        super("Product can't update  because" + uuid);
    }
}
