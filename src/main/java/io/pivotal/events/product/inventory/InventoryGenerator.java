package io.pivotal.events.product.inventory;

import io.pivotal.events.product.event.ProductCreatedEvent;
import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryGenerator {

    private final ProductService productService;

    @EventListener
    void handle(ProductCreatedEvent event) {
        establishInventory(event.getProductEntity());
    }

    @SneakyThrows
    public void establishInventory(ProductEntity productEntity) {
        Thread.sleep(3000);
        log.info("Assigning inventory to {}", productEntity);
        InventoryStatusEntity inventoryStatus = InventoryStatusEntity.builder()
            .status(InventoryStatus.IN_STOCK)
            .quantity(100)
            .build();
        productEntity.applyInventoryStatus(inventoryStatus);
        productService.updateProduct(productEntity);
    }
}
