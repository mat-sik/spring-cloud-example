package com.github.matsik.commons.response;

import java.math.BigDecimal;

public record ProductResponse(
        String uniqId,
        String sku,
        String nameTitle,
        String description,
        BigDecimal listPrice,
        BigDecimal salePrice,
        String category,
        String categoryTree,
        String averageProductRating,
        String productUrl,
        String productImageUrls,
        String brand,
        int totalNumberReviews,
        String reviews
) {
}
