package com.github.matsik.product;

import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import com.github.matsik.product.client.catalog.CatalogClient;
import com.github.matsik.product.client.inventory.InventoryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CatalogClient catalogClient;
    private final InventoryClient inventoryClient;

    public ProductResponse findById(String id) {
        Set<String> availabilities = inventoryClient.getAvailabilities(List.of(id));

        if (availabilities.contains(id)) {
            throw new ProductUnavailableException(id);
        }
        return catalogClient.getProductById(id);
    }

    public PageResponse<ProductResponse> findAll(PageRequest pageRequest) {
        PageResponse<ProductResponse> pageResponse = catalogClient.getAllProducts(pageRequest);

        List<String> ids = pageResponse.content().stream()
                .map(ProductResponse::uniqId)
                .toList();

        Set<String> availabilities = inventoryClient.getAvailabilities(ids);

        List<ProductResponse> content = pageResponse.content().stream()
                .filter(product -> availabilities.contains(product.uniqId()))
                .toList();

        return new PageResponse<>(
                content,
                content.size()
        );
    }

}
