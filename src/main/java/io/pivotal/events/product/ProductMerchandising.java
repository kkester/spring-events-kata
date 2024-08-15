package io.pivotal.events.product;

import io.pivotal.events.product.inventory.InventoryChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMerchandising {

    private final InventoryChecker inventoryChecker;

    public List<ProductEntity> getProductsOnSale(List<ProductEntity> productEntities) {
        return productEntities.stream()
            .filter(inventoryChecker::check)
            .toList();
    }
}
