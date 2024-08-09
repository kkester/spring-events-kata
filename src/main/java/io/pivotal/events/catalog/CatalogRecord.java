package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductRecord;

import java.time.LocalDate;
import java.util.List;

public record CatalogRecord(Long id, String name, String description, LocalDate startDate, LocalDate endDate, List<ProductRecord> products) {
}
