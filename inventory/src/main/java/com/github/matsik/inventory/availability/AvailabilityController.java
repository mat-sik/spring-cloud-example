package com.github.matsik.inventory.availability;

import com.github.matsik.commons.response.AvailabilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<List<AvailabilityResponse>> findAllByIds(@RequestParam(name = "id") List<String> ids) {
        List<Availability> availability = availabilityService.findAllByIds(ids);

        List<AvailabilityResponse> response = availability.stream()
                .map(AvailabilityController::mapToResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    private static AvailabilityResponse mapToResponse(Availability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getCount()
        );
    }

}
