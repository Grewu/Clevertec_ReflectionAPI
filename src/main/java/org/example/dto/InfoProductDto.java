package org.example.dto;


import java.math.BigDecimal;
import java.util.UUID;

public record InfoProductDto(

        /*
         * Идентификатор не может быть null
         */
        UUID uuid,

        /*
         * Имя продукта смотрите {@link org.example.entity.Product}
         */
        String name,

        /*
         * Описание продукта не может быть null, может быть пустой строкой
         */
        String description,

        /*
         * Стоимость не может быть null или негативным
         */
        BigDecimal price) {
}
