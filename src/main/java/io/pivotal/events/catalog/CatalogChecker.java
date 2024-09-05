package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.merch.ProductSaleResultType;
import io.pivotal.events.product.merch.ProductSaleSelector;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogChecker implements ProductSaleSelector {

    @SneakyThrows
    public ProductSaleResultType check(ProductEntity productEntity) {
        log.info("Checking Catalog for Product {}", productEntity.getSku());
        TimeUnit.MILLISECONDS.sleep(1000);

        ProductSaleResultType result;
        int random = new Random().nextInt(100);
        if (random > 50) {
            log.info("Product {} Selected by Catalog", productEntity.getSku());
            result = ProductSaleResultType.ACCEPTED;
        } else if (random > 25) {
            log.info("Product {} Declined by Catalog", productEntity.getSku());
            result = ProductSaleResultType.DECLINED;
        } else {
            log.info("Product {} Rejected by Catalog", productEntity.getSku());
            result = ProductSaleResultType.REJECTED;
        }
        return result;
    }
}
