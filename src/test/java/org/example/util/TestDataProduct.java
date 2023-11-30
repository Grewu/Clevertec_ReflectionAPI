package org.example.util;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class TestDataProduct {
    @Builder.Default
    private UUID uuid = UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962");

    @Builder.Default
    private String name = "name";

    @Builder.Default
    private String description = "description";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(10);

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, Month.NOVEMBER, 29, 21, 0, 34);

    public Product buildProduct() {
        return new Product(uuid, name, description, price, created);
    }

    public ProductDto buildProductDTO() {
        return new ProductDto(uuid, name, description, price, created);
    }

    public InfoProductDto buidInfoProductDto() {
        return new InfoProductDto(uuid, name, description, price);
    }

}
