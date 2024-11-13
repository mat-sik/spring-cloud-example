package com.github.matsik.product;

import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import com.github.matsik.product.client.catalog.CatalogRemoteService;
import com.github.matsik.product.client.inventory.InventoryRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CatalogRemoteService catalogRemoteService;
    private final InventoryRemoteService inventoryRemoteService;

    public ProductResponse findById(String id) {
        Set<String> availabilities = inventoryRemoteService.getAvailabilities(List.of(id));

        if (availabilities.contains(id)) {
            throw new ProductUnavailableException(id);
        }
        return catalogRemoteService.getProductById(id);
    }

    public PageResponse<ProductResponse> findAll(PageRequest pageRequest) {
        PageResponse<ProductResponse> pageResponse = catalogRemoteService.getAllProducts(pageRequest);

        List<String> ids = pageResponse.content().stream()
                .map(ProductResponse::uniqId)
                .toList();

        Set<String> availabilities = inventoryRemoteService.getAvailabilities(ids);

        List<ProductResponse> content = pageResponse.content().stream()
                .filter(product -> availabilities.contains(product.uniqId()))
                .toList();

        return new PageResponse<>(
                content,
                content.size()
        );
    }

}
