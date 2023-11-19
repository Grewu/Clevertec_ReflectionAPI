package org.example;

import lombok.AllArgsConstructor;
import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
public class Main {
    private final ProductService productService;

    private void method() {
        String dateString = "2023-11-14 13:31:17.227031";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        ProductDto productDto = new ProductDto(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), "Product Name",
                "Product Description", BigDecimal.TEN, dateTime);
        System.out.println(productService.get(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962")));
        productService.delete((UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962")));

        System.out.println(productService.get(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962")));
        productService.getAll();
        productService.update(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), productDto);

    }

    public static void main(String[] args) {
        ProductMapper mapper = new ProductMapperImpl();
        ProductDao productDao = new ProductDaoImpl(mapper);
        ProductService productService1 = new ProductServiceImpl(mapper, productDao);
        Main main = new Main(productService1);
        main.method();
    }
}
