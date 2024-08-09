package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryGenerator {

    private final ProductService productService;

    public void establishInventory(ProductRecord productRecord) {
        InventoryStatusRecord inventoryStatus = new InventoryStatusRecord(
            InventoryStatus.IN_STOCK,
            100
        );
        productService.updateProduct(new ProductRecord(
            productRecord.id(),
            productRecord.name(),
            productRecord.description(),
            productRecord.sku(),
            productRecord.createdDate(),
            inventoryStatus
        ));
    }
}
