package com.github.matsik.product.client.catalog;

public class CatalogServiceUnavailableException extends RuntimeException {
    private static final String MESSAGE = "Catalog service is currently unavailable.";
    public CatalogServiceUnavailableException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
