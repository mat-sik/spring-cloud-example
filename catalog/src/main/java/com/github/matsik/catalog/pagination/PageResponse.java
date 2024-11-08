package com.github.matsik.catalog.pagination;

import java.util.List;

public record PageResponse<T>(List<T> content, long size) {
}
