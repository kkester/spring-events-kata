package io.pivotal.events.product;

import io.pivotal.events.catalog.CatalogEntity;
import io.pivotal.events.product.inventory.InventoryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @SneakyThrows
    public ProductRecord getProductById(Long productId) {
        Thread.sleep(1000);
        log.info("Getting product for {}", productId);
        return null;
    }

    public void createProduct(NewProductRecord productRecord) {
        log.info("Creating product {}", productRecord);

    }

    public void updateProduct(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @SneakyThrows
    public void assignProductsTo(CatalogEntity catalogEntity) {
        Thread.sleep(3000);
        log.info("Assigning Products to {}", catalogEntity);
    }
}
