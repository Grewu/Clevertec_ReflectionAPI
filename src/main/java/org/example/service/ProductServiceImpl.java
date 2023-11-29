package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dao.ProductDao;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.exception.ProductNotFoundException;
import org.example.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductDao productDao;


    @Override
    public InfoProductDto get(UUID uuid) {
        return productDao.findById(uuid)
                .map(mapper::toInfoProductDto)
                .orElseThrow(() -> new ProductNotFoundException(uuid));
    }

    @Override
    public List<InfoProductDto> getAll() {
        List<Product> productDtos = productDao.findAll();
        return productDtos.stream()
                .map(mapper::toInfoProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        Product saved = productDao.save(product);
        return saved.getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        Optional<Product> productOptional = productDao.findById(uuid);
        productOptional.ifPresent(existingProduct -> {
            mapper.merge(existingProduct, productDto);
            productDao.save(existingProduct);
        });
    }

    @Override
    public void delete(UUID uuid) {
        productDao.delete(uuid);
    }
}
