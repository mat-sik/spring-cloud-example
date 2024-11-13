package com.github.matsik.product.client.inventory;

import com.github.matsik.commons.response.AvailabilityResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log
public class InventoryRemoteService {

    private final InventoryClient inventoryClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public Set<String> getAvailabilities(List<String> ids) {
        return circuitBreakerFactory.create("InventoryRemoteService#getAvailabilities")
                .run(() -> getAvailableProductIds(ids), this::fallbackFilterOutUnavailable);
    }

    private Set<String> getAvailableProductIds(List<String> ids) {
        List<AvailabilityResponse> response = inventoryClient.getAvailabilityById(ids);
        return response.stream()
                .filter(res -> res.count() > 0)
                .map(AvailabilityResponse::id)
                .collect(Collectors.toSet());
    }

    private Set<String> fallbackFilterOutUnavailable(Throwable cause) {
        log.info("Fallback has been called. Cause: " + cause.toString());
        throw new InventoryServiceUnavailableException(cause);
    }
}
