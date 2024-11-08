package com.github.matsik.inventory.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping("/{id}")
    public ResponseEntity<Availability> findById(@PathVariable String id) {
        Availability availability = availabilityService.findById(id);
        return ResponseEntity.ok(availability);
    }

}
