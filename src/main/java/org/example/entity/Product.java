package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Product implements ProductBuilder {

    /**
     * Идентификатор продукта (генерируется базой)
     */
    private UUID uuid;

    /**
     * Название продукта (не может быть null или пустым, содержит 5-10 символов(русский или пробелы))
     */
    private String name;

    /**
     * Описание продукта(может быть null или 10-30 символов(русский и пробелы))
     */
    private String description;

    /**
     * Не может быть null и должен быть положительным
     */
    private BigDecimal price;

    /**
     * Время создания, не может быть null(задаётся до сохранения и не обновляется)
     */
    private LocalDateTime created;

    @Override
    public Product build() {
        return this;
    }

    @Override
    public ProductBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public ProductBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public ProductBuilder withCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

}
