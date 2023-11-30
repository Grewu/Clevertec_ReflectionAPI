package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductBuilder {
    Product build();

    ProductBuilder withUuid(UUID uuid);

    ProductBuilder withName(String name);

    ProductBuilder withDescription(String description);

    ProductBuilder withPrice(BigDecimal price);

    ProductBuilder withCreated(LocalDateTime created);
}
