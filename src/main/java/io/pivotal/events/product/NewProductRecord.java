package io.pivotal.events.product;

public record NewProductRecord(String name,
                               String description,
                               String sku) {
}
