package com.github.matsik.product.client.catalog;

import com.github.matsik.commons.response.PageResponse;
import com.github.matsik.commons.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "${client.catalog.eurekaServiceId}")
interface CatalogClient {

    @GetMapping("products/{id}")
    ProductResponse getProductById(@PathVariable("id") String id);

    @GetMapping("products")
    PageResponse<ProductResponse> getAllProducts(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam boolean ascending
    );

}
