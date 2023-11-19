package org.example.exception;

import java.sql.SQLException;

public class ProductCreateException extends RuntimeException {
    public ProductCreateException(SQLException sqlException) {
        super("Product can't create  because" + sqlException);
    }
}
