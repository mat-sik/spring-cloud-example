package com.github.matsik.product.client.inventory;

import com.github.matsik.commons.response.AvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "${client.inventory.eurekaServiceId}")
interface InventoryClient {
    @GetMapping("availabilities")
    List<AvailabilityResponse> getAvailabilityById(@RequestParam(name = "id") List<String> ids);
}
