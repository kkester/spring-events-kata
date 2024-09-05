package io.pivotal.events.product.merch;

import io.pivotal.events.product.ProductEntity;

public interface ProductSaleSelector {
    ProductSaleResultType check(ProductEntity product);
}
