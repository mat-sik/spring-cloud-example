package com.github.matsik.product.client.catalog;

import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import com.github.matsik.product.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class CatalogRemoteService {

    private final CatalogClient catalogClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public ProductResponse getProductById(String id) {
        return circuitBreakerFactory.create("CatalogRemoteService#getProductById")
                .run(() -> catalogClient.getProductById(id),
                        CatalogRemoteService::fallbackGetProductById);
    }

    private static ProductResponse fallbackGetProductById(Throwable cause) {
        log.info("Fallback has been called. Cause: " + cause.toString());
        throw new CatalogServiceUnavailableException(cause);
    }

    public PageResponse<ProductResponse> getAllProducts(PageRequest request) {
        return circuitBreakerFactory.create("CatalogRemoteService#getAllProducts")
                .run(() -> catalogClient.getAllProducts(request.pageNumber(), request.pageSize(), request.ascending()),
                        CatalogRemoteService::fallbackGetAllProducts);
    }

    private static PageResponse<ProductResponse> fallbackGetAllProducts(Throwable cause) {
        log.info("Fallback has been called. Cause: " + cause.toString());
        throw new CatalogServiceUnavailableException(cause);
    }

}
