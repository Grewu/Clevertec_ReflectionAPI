package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.cache.Cache;
import org.example.dao.ProductDao;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.exception.ProductNotFoundException;
import org.example.mapper.ProductMapper;
import org.example.proxy.ProductProxy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductDao productDao;
    private final ProductProxy productProxy;

    @Override
    public InfoProductDto get(UUID uuid) {
        productProxy.beforeGet(uuid);
        Optional<Product> productOptional = productDao.findById(uuid);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            InfoProductDto result = mapper.toInfoProductDto(product);
            productProxy.afterGet(uuid, result);
            return result;
        } else {
            throw new ProductNotFoundException(uuid);
        }
    }

    @Override
    public List<InfoProductDto> getAll() {
        List<ProductDto> productDtos = productDao.getAll();
        List<InfoProductDto> result = productDtos.stream()
                .map(mapper::toInfoProductDto)
                .collect(Collectors.toList());
        productProxy.afterGetAll(result);
        return result;
    }

    @Override
    public UUID create(ProductDto productDto) {
        productProxy.beforeCreate(productDto);
        Product product = mapper.toProduct(productDto);
        Product saved = productDao.save(product);
        UUID result = saved.getUuid();
        productProxy.afterCreate(productDto);
        return result;
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        productProxy.beforeUpdate(uuid, productDto);
        Optional<Product> productOptional = productDao.findById(uuid);
        productOptional.ifPresent(existingProduct -> {
            existingProduct.setName(productDto.name());
            existingProduct.setDescription(productDto.description());
            existingProduct.setPrice(productDto.price());

            productDao.save(existingProduct);
        });
        productProxy.afterUpdate(productDto);
    }

    @Override
    public void delete(UUID uuid) {
        productProxy.beforeDelete(uuid);
        productDao.delete(uuid);
        productProxy.afterDelete(uuid);
    }
}
