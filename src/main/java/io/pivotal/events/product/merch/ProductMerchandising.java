package io.pivotal.events.product.merch;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.event.ProductSaleCheckEvent;
import io.pivotal.events.product.event.ProductSaleEvent;
import io.pivotal.events.product.event.ProductSaleResultType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.modulith.events.EventPublication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductMerchandising {

    private final ApplicationEventPublisher eventPublisher;
    private final CompletedEventPublications completedEventPublications;

    public List<Long> getProductsOnSale(List<ProductEntity> productEntities) {
        UUID requestId = UUID.randomUUID();
        productEntities.stream()
                .map(productEntity -> new ProductSaleCheckEvent(requestId, productEntity))
                .forEach(eventPublisher::publishEvent);

        boolean finished = false;
        LocalDateTime timeOut = LocalDateTime.now().plus(Duration.ofMillis(10000));
        List<ProductSaleEvent> eventPublications = emptyList();
        while (!finished && timeOut.isAfter(LocalDateTime.now())) {
            eventPublications = getEventPublications(requestId);
            finished = productEntities.size() == eventPublications.size();
        }

        return processResults(eventPublications);
    }

    private static List<Long> processResults(List<ProductSaleEvent> eventPublications) {
        Map<Long, List<ProductSaleResultType>> collect = eventPublications.stream()
                .collect(groupingBy(ProductSaleEvent::getProductId, mapping(ProductSaleEvent::getResult, toList())));
        return collect.entrySet().stream()
                .filter(e -> !e.getValue().contains(ProductSaleResultType.REJECTED))
                .filter(e -> e.getValue().contains(ProductSaleResultType.ACCEPTED))
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<ProductSaleEvent> getEventPublications(UUID requestId) {
        return completedEventPublications.findAll().stream()
                .map(EventPublication::getEvent)
                .map(ProductSaleEvent.class::cast)
                .filter(e -> e.getRequestId().equals(requestId))
                .toList();
    }

    @ApplicationModuleListener
    void handle(ProductSaleEvent event) {
        log.info("Product {} {} Recorded", event.getSku(), event.getResult());
    }
}
