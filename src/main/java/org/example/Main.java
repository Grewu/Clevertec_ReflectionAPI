package org.example;

import lombok.AllArgsConstructor;
import org.example.controller.Controller;
import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;
import org.example.util.pdf.PDFWriter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
public class Main {
    private final Controller productController;

    private void method() {
        String dateString = "2023-11-14 13:31:17.227031";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        ProductDto productDto = new ProductDto(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), "Product Name",
                "Product Description", BigDecimal.TEN, dateTime);
        Product product = new Product(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), "name", "description",
                BigDecimal.ONE, LocalDateTime.now());
//        System.out.println(productController.getProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962")));
//        productController.deleteProduct((UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962")));
        PDFWriter.createPdfWithBackground(product);
//        System.out.println(productController.createProduct(productDto));
//        productController.getAllProducts();
//        productController.updateProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), productDto);

    }

    public static void main(String[] args) {
        ProductMapper mapper = new ProductMapperImpl();
        ProductDao productDao = new ProductDaoImpl(mapper);
        ProductService productService1 = new ProductServiceImpl(mapper, productDao);
        Controller controller = new Controller(productService1);
        Main main = new Main(controller);
        main.method();
    }
}
