package com.github.matsik.inventory.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public Availability findById(String id) {
        Optional<Availability> availability = availabilityRepository.findById(id);
        return availability.orElseThrow(() -> new AvailabilityNotFoundException(id));
    }

}
