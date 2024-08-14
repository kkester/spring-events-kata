package io.pivotal.events.product.event;

import io.pivotal.events.product.ProductEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class ProductSaleSelectedEvent extends ApplicationEvent {
    private final UUID requestId;
    private final ProductEntity productEntity;

    public ProductSaleSelectedEvent(UUID requestId, ProductEntity productEntity) {
        super(productEntity);
        this.requestId = requestId;
        this.productEntity = productEntity;
    }
}
