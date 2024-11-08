package com.github.matsik.product;

public record PageRequest(int pageNumber, int pageSize, boolean ascending) {
}
