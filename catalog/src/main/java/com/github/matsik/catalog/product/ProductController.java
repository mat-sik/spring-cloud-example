package com.github.matsik.catalog.product;

import com.github.matsik.catalog.pagination.PageResponse;
import com.github.matsik.catalog.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("all")
    public ResponseEntity<PageResponse<Product>> findAllBySku(@RequestBody Pagination pagination) {
        Sort sort = getSkuSort(pagination.ascending());
        PageRequest pageRequest = PageRequest.of(pagination.pageNumber(), pagination.pageSize(), sort);

        Page<Product> page = productService.findAllBySku(pageRequest);

        PageResponse<Product> response = new PageResponse<>(
                page.getContent(),
                page.getTotalElements(),
                pagination
        );

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
