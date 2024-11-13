package com.github.matsik.product.client.inventory;

public class InventoryServiceUnavailableException extends RuntimeException {
    private static final String MESSAGE = "Inventory service is unavailable.";
    public InventoryServiceUnavailableException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
