package io.pivotal.events.product;

import io.pivotal.events.product.inventory.InventoryStatusRecord;

import java.time.LocalDateTime;

public record ProductRecord(Long id,
                            String name,
                            String description,
                            String sku,
                            InventoryStatusRecord inventoryStatus) {
}
