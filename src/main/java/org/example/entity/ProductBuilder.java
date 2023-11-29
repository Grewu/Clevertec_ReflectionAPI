package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductBuilder {
    Product build();

    ProductBuilder uuid(UUID uuid);

    ProductBuilder name(String name);

    ProductBuilder description(String description);

    ProductBuilder price(BigDecimal price);

    ProductBuilder created(LocalDateTime created);
}
