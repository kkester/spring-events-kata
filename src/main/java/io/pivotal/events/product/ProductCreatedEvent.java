package io.pivotal.events.product;

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
