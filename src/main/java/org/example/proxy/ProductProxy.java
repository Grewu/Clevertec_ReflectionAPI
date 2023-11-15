package org.example.proxy;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cache.Cache;
import org.example.cache.LFUCache;
import org.example.cache.LRUCache;
import org.example.dao.ProductDao;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.exception.ProductCacheException;
import org.example.util.YmlReader;

import java.util.List;
import java.util.UUID;


@Aspect
public final class ProductProxy {
    private final ProductDao productDao;
    private Cache<UUID, ProductDto> productDtoCache;
    private Cache<UUID, InfoProductDto> productInfoProducDtoCache;

    public ProductProxy(ProductDao productDao) {
        this.productDao = productDao;
        String cacheType = YmlReader.getCacheType();
        if ("LRU".equals(cacheType)) {
            productDtoCache = new LRUCache<>(Integer.parseInt(YmlReader.getCacheCapacity()));
            productInfoProducDtoCache = new LRUCache<>(Integer.parseInt(YmlReader.getCacheCapacity()));
        } else if ("LFU".equals(cacheType)) {
            productDtoCache = new LFUCache<>(Integer.parseInt(YmlReader.getCacheCapacity()));
            productInfoProducDtoCache = new LFUCache<>(Integer.parseInt(YmlReader.getCacheCapacity()));
        }
    }

    @Pointcut("@annotation(org.example.proxy.annotation.GetProduct) && args(uuid)")
    public void get(UUID uuid) {
    }

    @Pointcut("@annotation(org.example.proxy.annotation.GetAllProduct)")
    public void getAllProduct() {
    }

    @Pointcut("@annotation(org.example.proxy.annotation.CreateProduct) && args(productDto)")
    public void create(ProductDto productDto) {
    }

    @Pointcut("@annotation(org.example.proxy.annotation.UpdateProduct) && args(uuid, productDto)")
    public void update(UUID uuid, ProductDto productDto) {
    }

    @Pointcut("@annotation(org.example.proxy.annotation.DeleteProduct) && args(uuid)")
    public void delete(UUID uuid) {
    }

    @Before("get(uuid)")
    public void beforeGet(UUID uuid) {
        if (productDtoCache.get(uuid) == null) {
            throw new ProductCacheException();
        }
    }

    @AfterReturning(pointcut = "get(uuid)", returning = "result")
    public void afterGet(UUID uuid, InfoProductDto result) {
        if (productInfoProducDtoCache.get(uuid) == null) {
            productInfoProducDtoCache.set(uuid, result);
        }
    }

    @AfterReturning(pointcut = "getAllProduct()", returning = "result")
    public void afterGetAll(Object result) {
        if (result instanceof List<?>) {
            List<ProductDto> productDtoList = (List<ProductDto>) result;
            for (ProductDto productDto : productDtoList) {
                productDtoCache.set(productDto.uuid(), productDto);
            }
        }
    }

    @Before("create(productDto)")
    public void beforeCreate(ProductDto productDto) {
        if (productDtoCache.get(productDto.uuid()) == null) {
            productDao.create(productDto);
        }
    }

    @AfterReturning(pointcut = "create(productDto)", returning = "productDto")
    public void afterCreate(ProductDto productDto) {
        if (productDtoCache.get(productDto.uuid()) == null) {
            productDtoCache.set(productDto.uuid(), productDto);
        }
    }

    @Before("update(uuid,productDto)")
    public void beforeUpdate(UUID uuid, ProductDto productDto) {
        if (productDtoCache.get(uuid) == null) {
            throw new ProductCacheException();
        }
    }

    @AfterReturning(pointcut = "update(uuid,productDto)", returning = "productDto")
    public void afterUpdate(UUID uuid, ProductDto productDto) {
        if (productDtoCache.get(productDto.uuid()) == null) {
            productDtoCache.set(productDto.uuid(), productDto);
        }
    }

    @Before("delete(uuid)")
    public void beforeDelete(UUID uuid) {
        productDao.delete(uuid);
    }

    @AfterReturning(value = "delete(uuid)", returning = "uuid")
    public void afterDelete(UUID uuid) {
        productDtoCache.remove(uuid);
    }

}
