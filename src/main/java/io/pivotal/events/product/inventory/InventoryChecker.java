package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.event.ProductSaleCheckEvent;
import io.pivotal.events.product.event.ProductSaleRejectedEvent;
import io.pivotal.events.product.event.ProductSaleSelectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryChecker {

    private final ApplicationEventMulticaster applicationEventMulticaster;

    @EventListener
    @SneakyThrows
    void handle(ProductSaleCheckEvent event) {
        Thread.sleep(1000);
        Random random = new Random();
        ProductEntity productEntity = event.getProductEntity();
        if (random.nextBoolean()) {
            log.info("Product {} Selected", productEntity.getSku());
            applicationEventMulticaster.multicastEvent(new ProductSaleSelectedEvent(event.getRequestId(), productEntity));
        } else {
            log.info("Product {} Rejected", productEntity.getSku());
            applicationEventMulticaster.multicastEvent(new ProductSaleRejectedEvent(event.getRequestId(), productEntity));
        }
    }
}
