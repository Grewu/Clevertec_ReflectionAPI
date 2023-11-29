package org.example.exception;

public class PDFException extends RuntimeException {
    public PDFException(Exception e) {
        super("PDF error " + e);
    }
}
