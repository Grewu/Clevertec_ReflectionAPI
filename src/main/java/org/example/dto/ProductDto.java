package org.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDto(
        /*
         * {@link org.example.entity.Product}
         */
        @NotNull
        UUID uuid,
        /*
         * {@link org.example.entity.Product}
         */
        @NotNull(message = "Имя должно быть задано")
        String name,
        /*
         * {@link org.example.entity.Product}
         */
        @NotBlank(message = "Описание не должно быть пустым")
        @Pattern(regexp = "^[A-Za-z0-9_-]*$", message = "Некорректный формат данных")
        String description,
        /*
          {@link org.example.entity.Product}
         */
        @NotNull
        @Positive(message = "Цена должна быть положительной")
        BigDecimal price,
        @NotNull(message = "дата  не должна быть пустым")
        LocalDateTime created) {

}
