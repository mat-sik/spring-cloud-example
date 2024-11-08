package com.github.matsik.catalog.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new RuntimeException(String.format("Product with: %s does not exist", id)));
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllBySku(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

}
