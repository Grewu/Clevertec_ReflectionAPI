package org.example.dto;

import java.math.BigDecimal;

public record ProductDto(

        /*
         * {@link org.example.entity.Product}
         */
        String name,
        /*
         * {@link org.example.entity.Product}
         */
        String description,


        /*
          {@link org.example.entity.Product}
         */
        BigDecimal price) {
}
