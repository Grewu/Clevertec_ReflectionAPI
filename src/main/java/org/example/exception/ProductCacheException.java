package org.example.exception;


public class ProductCacheException extends RuntimeException {
    public ProductCacheException() {
        super("Product in cache %s not found");
    }
}
