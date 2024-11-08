package com.github.matsik.product;

import com.github.matsik.commons.response.AvailabilityResponse;
import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import com.github.matsik.product.client.catalog.CatalogClient;
import com.github.matsik.product.client.inventory.InventoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CatalogClient catalogClient;
    private final InventoryClient inventoryClient;

    public ProductResponse findById(String id) {
        if (!productIsAvailable(id)) {
            throw new ProductUnavailableException(id);
        }
        return catalogClient.getProductById(id);
    }

    public PageResponse<ProductResponse> findAll(PageRequest pageRequest) {
        PageResponse<ProductResponse> pageResponse = catalogClient.getAllProducts(pageRequest);

        List<ProductResponse> content = pageResponse.content().stream()
                .filter(product -> productIsAvailable(product.uniqId()))
                .toList();

        return new PageResponse<>(
                content,
                content.size()
        );
    }

    private boolean productIsAvailable(String id) {
        // todo: this is inefficient, there should exist endpoint that accepts a list of ids
        AvailabilityResponse response = inventoryClient.getAvailabilityById(id);
        return response.count() > 0;
    }

}
