package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CatalogRecord(Long id, String name, String description, LocalDate startDate, LocalDate endDate, LocalDateTime createdDate, List<ProductRecord> products) {
}
