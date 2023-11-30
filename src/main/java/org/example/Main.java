package org.example;

import lombok.AllArgsConstructor;
import org.example.controller.Controller;
import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.ProductDto;
import org.example.entity.Product;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.pattern.template.ReportProductTemplate;
import org.example.pattern.template.pdf.PDFWriter;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
public class Main {

    private final Controller productController;
    private final ReportProductTemplate productTemplate;

    private void executeDemo() {


        ProductDto productDto = new ProductDto(
                UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"),
                "Product Name",
                "Product Description",
                BigDecimal.TEN,
                LocalDateTime.now()
        );

        Product product = new Product().build()
                .withUuid(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"))
                .withName("name")
                .withDescription("description")
                .withPrice(BigDecimal.TEN)
                .withCreated(LocalDateTime.now())
                .build();


        productController.getProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"));
        productController.deleteProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"));
        productTemplate.generateReport(product);
        productController.createProduct(productDto);
        productController.getAllProducts();
        productController.updateProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), productDto);
    }

    public static void main(String[] args) {

        ProductMapper mapper = new ProductMapperImpl();
        ProductDao productDao = new ProductDaoImpl(mapper);
        ProductService productService = new ProductServiceImpl(mapper, productDao);
        Controller controller = new Controller(productService);
        ReportProductTemplate productTemplatePDF = new PDFWriter();


        Main main = new Main(controller, productTemplatePDF);
        main.executeDemo();
    }
}

