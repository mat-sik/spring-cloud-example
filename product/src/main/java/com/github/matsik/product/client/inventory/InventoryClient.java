package com.github.matsik.product.client.inventory;

import com.github.matsik.commons.response.AvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "${client.inventory.eurekaServiceId}")
public interface InventoryClient {

    @GetMapping("availabilities/{id}")
    AvailabilityResponse getAvailabilityById(@PathVariable("id") String id);
}
