package com.github.matsik.catalog.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super(String.format("Product with id: %s does not exist", id));
    }
}
