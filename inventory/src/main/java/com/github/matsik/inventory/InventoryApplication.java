package com.github.matsik.inventory;

import com.github.matsik.inventory.availability.Availability;
import com.github.matsik.inventory.availability.AvailabilityRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SpringBootApplication
@Log
public class InventoryApplication {

    private static final long MIN_AVAILABILITY = 0;
    private static final long MAX_AVAILABILITY = 3;

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            @Value("${csv.data.path}") String csvFilePath,
            AvailabilityRepository repository
    ) {
        return _ -> loadCsvData(csvFilePath, repository);
    }

    @Transactional
    private static void loadCsvData(String path, AvailabilityRepository repository) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<Availability> products = csvReader.readAll().stream()
                    .map(InventoryApplication::mapToAvailability)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            repository.saveAll(products);
        }
    }

    private static Availability mapToAvailability(String[] csvRow) {
        try {
            return Availability.builder()
                    .id(csvRow[0])
                    .count(ThreadLocalRandom.current().nextLong(MIN_AVAILABILITY, MAX_AVAILABILITY + 1))
                    .build();
        } catch (NumberFormatException ex) {
            log.info("Could not parse: " + Arrays.toString(csvRow));
            return null;
        }
    }

}
