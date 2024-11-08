package com.github.matsik.catalog;

import com.github.matsik.catalog.product.Product;
import com.github.matsik.catalog.product.ProductRepository;
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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
@Log
public class CatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            @Value("${csv.data.path}") String csvFilePath,
            ProductRepository repository
    ) {
        return _ -> loadCsvData(csvFilePath, repository);
    }

    @Transactional
    private static void loadCsvData(String path, ProductRepository repository) throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader(path))) {
            List<Product> products = csvReader.readAll().stream()
                    .map(CatalogApplication::mapToProduct)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            repository.saveAll(products);
        }
    }

    private static Product mapToProduct(String[] csvRow) {
        try {
            return Product.builder()
                    .uniqId(csvRow[0])
                    .sku(csvRow[1])
                    .nameTitle(csvRow[2])
                    .description(csvRow[3])
                    .listPrice(new BigDecimal(csvRow[4]))
                    .salePrice(new BigDecimal(csvRow[5]))
                    .category(csvRow[6])
                    .categoryTree(csvRow[7])
                    .averageProductRating(csvRow[8])
                    .productUrl(csvRow[9])
                    .productImageUrls(csvRow[10])
                    .brand(csvRow[11])
                    .totalNumberReviews(Integer.parseInt(csvRow[12]))
                    .reviews(csvRow[13])
                    .build();
        } catch (NumberFormatException ex) {
            log.info("Could not parse: " + Arrays.toString(csvRow));
            return null;
        }
    }

}
