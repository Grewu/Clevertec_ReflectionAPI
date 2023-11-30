package org.example.pattern.template.pdf;

import org.example.util.TestDataProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PDFWriterTest {
    @Mock
    private PDFWriter testPdfWriter;

    @Test
    void createPdfWithProduct() {
        TestDataProduct expected = TestDataProduct.builder().build();
        TestDataProduct actual = TestDataProduct.builder().build();
        testPdfWriter.initialize(actual.buildProduct());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEquals(expected.getCreated(), actual.getCreated());
    }


    @Test
    void fileIsExists() {
        TestDataProduct testDataProduct = TestDataProduct.builder().build();
        testPdfWriter.createPdfWithBackground(testDataProduct.buildProduct());
        assertTrue(new File("check.pdf").exists());
    }

    @Test
    void pdfIsNotEmpty() {
        TestDataProduct testDataProduct = TestDataProduct.builder().build();
        testPdfWriter.createPdfWithBackground(testDataProduct.buildProduct());

        File pdfFile = new File("check.pdf");
        assertTrue(pdfFile.exists());
        assertTrue(pdfFile.length() > 0);
    }

}