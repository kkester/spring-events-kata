package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.event.ProductSaleCheckEvent;
import io.pivotal.events.product.event.ProductSaleEvent;
import io.pivotal.events.product.event.ProductSaleResultType;
import io.pivotal.events.product.merch.ProductSaleSelector;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryChecker implements ProductSaleSelector {

    private final ApplicationEventPublisher eventPublisher;

    @Async
    @EventListener
    @Transactional
    @SneakyThrows
    void handle(ProductSaleCheckEvent event) {
        ProductEntity productEntity = event.getProductEntity();
        log.info("Checking Inventory for Product {}", productEntity.getSku());
        TimeUnit.MILLISECONDS.sleep(1000);

        int random = new Random().nextInt(100);
        if (random > 50) {
            log.info("Product {} Selected", productEntity.getSku());
            eventPublisher.publishEvent(ProductSaleEvent.builder()
                    .requestId(event.getRequestId())
                    .productId(productEntity.getId())
                    .sku(productEntity.getSku())
                    .result(ProductSaleResultType.ACCEPTED)
                    .build());
        } else if (random > 25) {
            log.info("Product {} Declined", productEntity.getSku());
            eventPublisher.publishEvent(ProductSaleEvent.builder()
                    .requestId(event.getRequestId())
                    .productId(productEntity.getId())
                    .sku(productEntity.getSku())
                    .result(ProductSaleResultType.DECLINED)
                    .build());
        } else {
            log.info("Product {} Rejected", productEntity.getSku());
            eventPublisher.publishEvent(ProductSaleEvent.builder()
                    .requestId(event.getRequestId())
                    .productId(productEntity.getId())
                    .sku(productEntity.getSku())
                    .result(ProductSaleResultType.REJECTED)
                    .build());
        }
    }
}
