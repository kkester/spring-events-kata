package io.pivotal.events.product.event;

import io.pivotal.events.product.merch.ProductSaleResultType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProductSaleEvent {
    private UUID requestId;
    private Long productId;
    private String sku;
    private ProductSaleResultType result;
}
