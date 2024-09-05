package io.pivotal.events.product.merch;

import io.pivotal.events.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductMerchandising {

    private final List<ProductSaleSelector> productSaleSelectors;

    public List<ProductEntity> getProductsOnSale(List<ProductEntity> productEntities) {
        Map<ProductEntity, List<ProductSaleResultType>> productResultsMap = new HashMap<>();
        productEntities.forEach(productEntity ->
                productResultsMap.put(productEntity, productSaleSelectors.stream().map(selector -> selector.check(productEntity)).toList())
        );
        return productResultsMap.entrySet().stream()
                .filter(e -> !e.getValue().contains(ProductSaleResultType.REJECTED))
                .filter(e -> e.getValue().contains(ProductSaleResultType.ACCEPTED))
                .map(Map.Entry::getKey)
                .toList();
    }
}
