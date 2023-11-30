package org.example.proxy;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cache.Cache;
import org.example.cache.LRUCache;
import org.example.entity.Product;
import org.example.yml.YmlReader;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Aspect
public class ProductProxy {
    private final Cache<UUID, Product> productDtoCache;
    private static final String ALGORITM = YmlReader.getCacheType();
    private static final String CAPACITY = YmlReader.getCacheCapacity();

    public ProductProxy() {
        if ("LFU".equalsIgnoreCase(ALGORITM)) {
            this.productDtoCache = new LRUCache<>(Integer.parseInt(CAPACITY));
        } else if ("LRU".equalsIgnoreCase(ALGORITM)) {
            this.productDtoCache = new LRUCache<>(Integer.parseInt(CAPACITY));
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Pointcut("@annotation(org.example.proxy.annotation.GetProduct)")
    public void getProduct() {

    }

    @Pointcut("@annotation(org.example.proxy.annotation.CreateProduct)")
    public void createProduct() {

    }

    @Pointcut("@annotation(org.example.proxy.annotation.DeleteProduct)")
    public void deleteProduct() {

    }


    @Around("getProduct() && args(uuid)")
    public Object getProduct(ProceedingJoinPoint joinPoint, UUID uuid) throws Throwable {
        Product product = productDtoCache.get(uuid);
        if (Objects.nonNull(product)) {
            return Optional.of(product);
        }

        Optional<Product> result = (Optional<Product>) joinPoint.proceed();
        result.ifPresent(product1 -> productDtoCache.set(product1.getUuid(), product1));
        return result;
    }

    @Around("createProduct()  && args(product)")
    public Object createProduct(ProceedingJoinPoint joinPoint, Product product) throws Throwable {
        UUID productId = (UUID) joinPoint.proceed();
        productDtoCache.set(productId, product);
        return productId;
    }

    @Around("deleteProduct()")
    public Object deleteProduct(ProceedingJoinPoint joinPoint) throws Throwable {
        UUID productId = (UUID) joinPoint.proceed();
        productDtoCache.remove(productId);
        return productId;
    }

}



