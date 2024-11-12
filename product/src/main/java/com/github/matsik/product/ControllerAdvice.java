package com.github.matsik.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matsik.product.client.catalog.CatalogServiceUnavailableException;
import com.github.matsik.product.client.inventory.InventoryServiceUnavailableException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(ProductUnavailableException.class)
    public ProblemDetail onProductUnavailableException(ProductUnavailableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CatalogServiceUnavailableException.class)
    public ProblemDetail onCatalogServiceUnavailableException(CatalogServiceUnavailableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(InventoryServiceUnavailableException.class)
    public ProblemDetail onInventoryServiceUnavailableException(InventoryServiceUnavailableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ProblemDetail onFeignException(FeignException.NotFound feignException) {
        try {
            Optional<ProblemDetail> problemDetail = parseProblemDetail(feignException);
            return problemDetail.orElseGet(() -> ProblemDetail.forStatus(HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing the problem details.");
        }
    }

    private Optional<ProblemDetail> parseProblemDetail(FeignException.NotFound feignException) throws Exception {
        Optional<ByteBuffer> body = feignException.responseBody();
        if (body.isPresent()) {
            ByteBuffer bodyValue = body.get();
            Map<?, ?> errorDetails = objectMapper.readValue(bodyValue.array(), Map.class);

            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
            problemDetail.setDetail((String) errorDetails.get("detail"));

            return Optional.of(problemDetail);
        }
        return Optional.empty();
    }

}
