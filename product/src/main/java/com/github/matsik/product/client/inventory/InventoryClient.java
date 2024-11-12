package com.github.matsik.product.client.inventory;

import com.github.matsik.commons.response.AvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@FeignClient(name = "${client.inventory.eurekaServiceId}")
public interface InventoryClient {

    @GetMapping("availabilities")
    List<AvailabilityResponse> getAvailabilityById(@RequestParam(name = "id") List<String> ids);

    default Set<String> getAvailabilities(List<String> ids) {
        List<AvailabilityResponse> response = getAvailabilityById(ids);

        return response.stream()
                .filter(res -> res.count() > 0)
                .map(AvailabilityResponse::id)
                .collect(Collectors.toSet());
    }
}
