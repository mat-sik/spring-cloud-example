package com.github.matsik.catalog.product;

import com.github.matsik.catalog.pagination.PageResponse;
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

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Product>> findAllBySku(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = getSkuSort(ascending);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productService.findAllBySku(pageRequest);

        PageResponse<Product> response = new PageResponse<>(page.getContent(), page.getSize());
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

}
