package io.pivotal.events.product.event;

import io.pivotal.events.product.ProductEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductCreatedEvent extends ApplicationEvent {
    private final ProductEntity productEntity;

    public ProductCreatedEvent(ProductEntity productEntity) {
        super(productEntity);
        this.productEntity = productEntity;
    }
}
