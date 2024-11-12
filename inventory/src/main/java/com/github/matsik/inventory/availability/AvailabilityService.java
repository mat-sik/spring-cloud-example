package com.github.matsik.inventory.availability;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public List<Availability> findAllByIds(List<String> ids) {
        return availabilityRepository.findAllByIdIn(ids);
    }

}
