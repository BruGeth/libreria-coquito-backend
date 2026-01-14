package com.coquito.backend.exception;

public class InvalidSaleException extends RuntimeException {

    public InvalidSaleException(String message) {
        super(message);
    }
}
