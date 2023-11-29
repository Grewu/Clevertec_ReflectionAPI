package org.example.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record InfoProductDto(

        /*
         * Идентификатор не может быть null
         */
        @NotNull
        UUID uuid,

        /*
         * Имя продукта смотрите {@link org.example.entity.Product}
         */
        @NotNull(message = "Имя должно быть задано")
        String name,

        /*
         * Описание продукта не может быть null, может быть пустой строкой
         */
        @NotBlank(message = "Описание не должно быть пустым")
        @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Некорректный формат данных")
        String description,

        /*
         * Стоимость не может быть null или негативным
         */
        @NotNull
        @Positive(message = "Цена должна быть положительной")
        BigDecimal price) {

}
