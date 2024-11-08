package com.github.matsik.product;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String id) {
        super(String.format("Product with id: %s is unavailable.", id));
    }
}
