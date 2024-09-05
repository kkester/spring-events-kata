package io.pivotal.events.product.merch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.CompletedEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductSaleEventManager {

    private final CompletedEventPublications completedEventPublications;

    @Scheduled(fixedRate = 30000)
    public void cleanUpEvents() {
        completedEventPublications.deletePublicationsOlderThan(Duration.ofMinutes(1));
    }
}
