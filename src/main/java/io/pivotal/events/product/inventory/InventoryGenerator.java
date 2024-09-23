package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryGenerator {

    private final ProductService productService;

    @SneakyThrows
    public void establishInventory(ProductEntity productEntity) {
        TimeUnit.MILLISECONDS.sleep(3000);
        log.info("Assigning inventory to {}", productEntity);
        InventoryStatusEntity inventoryStatus = InventoryStatusEntity.builder()
            .status(InventoryStatus.IN_STOCK)
            .quantity(100)
            .build();
        productEntity.applyInventoryStatus(inventoryStatus);
        productService.updateProduct(productEntity);
    }
}
