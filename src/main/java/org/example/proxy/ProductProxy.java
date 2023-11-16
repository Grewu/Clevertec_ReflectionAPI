package org.example.proxy;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.cache.Cache;
import org.example.cache.LFUCache;
import org.example.cache.LRUCache;
import org.example.dao.ProductDao;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.util.YmlReader;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Aspect
public class ProductProxy {
    private final Cache<UUID, ProductDto> productDtoCache;

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

    @Around("getProduct()")
    public Object getProduct(ProceedingJoinPoint joinPoint) throws Throwable {
        ProductDto productDto = new ProductDto(UUID.randomUUID(),"name","des", BigDecimal.TEN, LocalDateTime.MIN);
//
//        if (productDto == null) {
//            Object result = joinPoint.proceed();
//
//            if (result instanceof ProductDto) {
//                productDto = (ProductDto) result;
//                productDtoCache.set(uuid, productDto);
//            }
//        }
//
        return productDto;

    }


}
