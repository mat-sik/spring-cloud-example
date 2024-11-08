package com.github.matsik.product.client.catalog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${client.catalog.eurekaServiceId}")
@RequestMapping("/catalog")
public interface CatalogClient {

    @GetMapping("/products/{id}")
    void getProductById(@PathVariable("id") String id);

}
