package com.github.matsik.inventory.availability;

public class AvailabilityNotFoundException extends RuntimeException {
    public AvailabilityNotFoundException(String id) {
        super(String.format("Availability with id: %s does not exist", id));
    }
}
