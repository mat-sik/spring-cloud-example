package com.github.matsik.commons.response;

import java.util.List;

public record PageResponse<T>(List<T> content, long size) {
}
