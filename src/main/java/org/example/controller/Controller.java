package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.service.ProductService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class Controller {

    private final ProductService productService;

    public InfoProductDto getProduct(UUID productId) {
        return productService.get(productId);
    }

    public List<InfoProductDto> getAllProducts() {
        return productService.getAll();
    }

    public UUID createProduct(ProductDto productDto) {
        return productService.create(productDto);
    }

    public void updateProduct(UUID productId, ProductDto productDto) {
        productService.update(productId, productDto);
    }

    public void deleteProduct(UUID productId) {
        productService.delete(productId);
    }
}
