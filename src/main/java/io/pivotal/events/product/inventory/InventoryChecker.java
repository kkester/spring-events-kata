package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryChecker {

    @SneakyThrows
    public boolean check(ProductEntity productEntity) {
        Thread.sleep(1000);
        Random random = new Random();
        boolean result = random.nextBoolean();
        if (result) {
            log.info("Product {} Selected", productEntity.getSku());
        } else {
            log.info("Product {} Rejected", productEntity.getSku());
        }
        return result;
    }
}
