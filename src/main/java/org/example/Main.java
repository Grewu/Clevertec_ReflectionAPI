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
import org.example.util.template.ReportProductTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@AllArgsConstructor
public class Main {

    private final Controller productController;
    private final ReportProductTemplate productTemplate;

    private void executeDemo() {
        // Создаем объект LocalDateTime из строки
        String dateString = "2023-11-14 13:31:17.227031";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

        // Создаем объект ProductDto
        ProductDto productDto = new ProductDto(
                UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"),
                "Product Name",
                "Product Description",
                BigDecimal.TEN,
                LocalDateTime.now()
        );
        // Создаем объект Product
        Product product = new Product().build()
                .withUuid(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"))
                .withName("name")
                .withDescription("description")
                .withPrice(BigDecimal.TEN)
                .withCreated(LocalDateTime.now())
                .build();

        // Вызываем различные операции с контроллером и шаблоном
        productController.getProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"));
        productController.deleteProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"));
        productTemplate.generateReport(product);
        productController.createProduct(productDto);
        productController.getAllProducts();
        productController.updateProduct(UUID.fromString("e6cde702-960c-47e5-ac8c-acdc4abcf962"), productDto);
    }

    public static void main(String[] args) {
        // Инициализация зависимостей
        ProductMapper mapper = new ProductMapperImpl();
        ProductDao productDao = new ProductDaoImpl(mapper);
        ProductService productService = new ProductServiceImpl(mapper, productDao);
        Controller controller = new Controller(productService);
        ReportProductTemplate productTemplatePDF = new PDFWriter();

        // Создание объекта Main и выполнение демонстрационных операций
        Main main = new Main(controller, productTemplatePDF);
        main.executeDemo();
    }
}

