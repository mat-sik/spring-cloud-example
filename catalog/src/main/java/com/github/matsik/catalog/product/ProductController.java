package com.github.matsik.catalog.product;

import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        Product product = productService.findById(id);
        ProductResponse response = mapToResponse(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> findAllBySku(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = getSkuSort(ascending);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productService.findAllBySku(pageRequest);

        List<ProductResponse> content = page.getContent().stream()
                .map(ProductController::mapToResponse)
                .toList();

        PageResponse<ProductResponse> response = new PageResponse<>(content, page.getSize());
        return ResponseEntity.ok(response);
    }

    private static Sort getSkuSort(boolean ascending) {
        String fieldName = "sku";
        Sort.Order order = Sort.Order.desc(fieldName);
        if (ascending) {
            order = Sort.Order.asc(fieldName);
        }
        return Sort.by(order);
    }

    private static ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getUniqId(),
                product.getSku(),
                product.getNameTitle(),
                product.getDescription(),
                product.getListPrice(),
                product.getSalePrice(),
                product.getCategory(),
                product.getCategoryTree(),
                product.getAverageProductRating(),
                product.getProductUrl(),
                product.getProductImageUrls(),
                product.getBrand(),
                product.getTotalNumberReviews(),
                product.getReviews()
        );
    }
}
