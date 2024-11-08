package com.github.matsik.product;

import com.github.matsik.product.client.catalog.CatalogClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CatalogClient client) {
		return _ -> {
			var out = client.getAllProducts(0, 10, true);
			out.content().forEach(System.out::println);
		};
	}
}
