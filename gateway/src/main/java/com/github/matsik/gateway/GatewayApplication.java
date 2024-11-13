package com.github.matsik.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(
            @Value("${client.product.eurekaServiceId}") String productServiceId,
            RouteLocatorBuilder builder
    ) {
        return builder.routes()
                .route("product-by-id", r -> r.path("/products/{id}")
                        .uri("lb://" + productServiceId))
                .route("products-page", r -> r.path("/products")
                        .uri("lb://" + productServiceId))
                .build();
    }

}
