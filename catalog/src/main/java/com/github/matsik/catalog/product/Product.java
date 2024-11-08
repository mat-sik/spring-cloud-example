package com.github.matsik.catalog.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    private String uniqId;

    private String sku;

    private String nameTitle;

    @Lob
    private String description;

    private BigDecimal listPrice;

    private BigDecimal salePrice;

    private String category;

    private String categoryTree;

    private String averageProductRating;

    @Lob
    private String productUrl;

    @Lob
    private String productImageUrls;

    private String brand;

    private int totalNumberReviews;

    @Lob
    private String reviews;

}
