package io.pivotal.events.product;

import io.pivotal.events.product.event.ProductSaleCheckEvent;
import io.pivotal.events.product.event.ProductSaleRejectedEvent;
import io.pivotal.events.product.event.ProductSaleSelectedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMerchandising {

    private final ApplicationEventMulticaster applicationEventMulticaster;
    private Map<UUID,ConcurrentLinkedQueue<ProductEntity>> productsSelected = new ConcurrentHashMap<>();
    private Map<UUID,ConcurrentLinkedQueue<ProductEntity>> productsRejected = new ConcurrentHashMap<>();

    public List<ProductEntity> getProductsOnSale(List<ProductEntity> productEntities) {
        UUID requestId = initRequest();
        productEntities.stream()
            .map(productEntity -> new ProductSaleCheckEvent(requestId,productEntity))
            .forEach(applicationEventMulticaster::multicastEvent);
        boolean finished = false;
        while (!finished) {
            finished = haveAllProductsFinished(productEntities, requestId);
        };
        return processResults(requestId);
    }

    private boolean haveAllProductsFinished(List<ProductEntity> productEntities, UUID requestId) {
        return productEntities.size() == (productsSelected.get(requestId).size() + productsRejected.get(requestId).size());
    }

    private UUID initRequest() {
        UUID requestId = UUID.randomUUID();
        productsSelected.put(requestId, new ConcurrentLinkedQueue<>());
        productsRejected.put(requestId, new ConcurrentLinkedQueue<>());
        return requestId;
    }

    private List<ProductEntity> processResults(UUID requestId) {
        List<ProductEntity> results = productsSelected.get(requestId).stream().toList();
        productsSelected.remove(requestId);
        productsRejected.remove(requestId);
        return results;
    }

    @EventListener
    void handle(ProductSaleSelectedEvent event) {
        ProductEntity productEntity = event.getProductEntity();
        log.info("Product {} Selected Recorded", productEntity.getSku());
        productsSelected.get(event.getRequestId()).add(productEntity);
    }

    @EventListener
    void handle(ProductSaleRejectedEvent event) {
        ProductEntity productEntity = event.getProductEntity();
        log.info("Product {} Rejected Recorded", productEntity.getSku());
        productsRejected.get(event.getRequestId()).add(productEntity);
    }
}
