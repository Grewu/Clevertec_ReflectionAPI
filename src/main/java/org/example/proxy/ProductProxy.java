package org.example.proxy;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cache.Cache;
import org.example.dao.ProductDao;
import org.example.dto.ProductDto;
import org.example.exception.ProductCacheException;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Aspect
public class ProductProxy {
    private final ProductDao productDao;
    private final Cache<UUID, ProductDto> productDtoCache;

    @Pointcut("execution(* org.example.dao.ProductDao.get(..)) && args(uuid)")
    public void get(UUID uuid) {
    }

    @Before("get(uuid)")
    public void beforeGet(UUID uuid) {
        if (productDtoCache.get(uuid) == null) {
            throw new ProductCacheException();
        }
    }

    @AfterReturning(pointcut = "get(uuid)", returning = "result")
    public void afterGet(UUID uuid, ProductDto result) {
        if (productDtoCache.get(uuid) == null) {
            productDtoCache.set(uuid, result);
        }
    }

    @Pointcut("execution(* org.example.dao.ProductDao.getAll(..))")
    public void getAllProduct() {
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

    @Pointcut("execution(* org.example.dao.ProductDao.create(..)) && args(productDto)")
    public void create(ProductDto productDto) {

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

    @Pointcut("execution(* org.example.dao.ProductDao.update(..)) && args(uuid,productDto)")
    public void update(UUID uuid, ProductDto productDto) {

    }

    @Before("update(uuid,productDto)")
    public void beforeUpdate(UUID uuid, ProductDto productDto) {
        if (productDtoCache.get(uuid) == null) {
            throw new ProductCacheException();
        }
    }

    @AfterReturning(pointcut = "update(uuid,productDto)", returning = "productDto")
    public void afterUpdate(ProductDto productDto) {
        if (productDtoCache.get(productDto.uuid()) == null) {
            productDtoCache.set(productDto.uuid(), productDto);
        }
    }

    @Pointcut("execution(* org.example.dao.ProductDao.delete(..)) && args(uuid)")
    public void delete(UUID uuid) {
    }

    @Before("delete(uuid)")
    public void beforeDelete(UUID uuid) {
        productDao.delete(uuid);
    }

    @AfterReturning("delete(uuid)")
    public void afterDelete(UUID uuid) {
        productDtoCache.remove(uuid);
    }
}
