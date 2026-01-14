package com.coquito.backend.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String productName, Integer requested, Integer available) {
        super(String.format("Stock insuficiente para el producto '%s'. Solicitado: %d, Disponible: %d",
                productName, requested, available));
    }
}
