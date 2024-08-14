package io.pivotal.events.product;

import io.pivotal.events.catalog.CatalogEntity;
import io.pivotal.events.product.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMerchandising productMerchandising;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ApplicationEventMulticaster eventMulticaster;

    @SneakyThrows
    public ProductRecord getProductById(Long productId) {
        Thread.sleep(1000);
        log.info("Getting product for {}", productId);
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();
        return productMapper.productEntityToProductRecord(productEntity);
    }

    public ProductRecord createProduct(NewProductRecord productRecord) {
        log.info("Creating product {}", productRecord);
        ProductEntity productEntity = productMapper.newProductRecordToProductEntity(productRecord);
        productRepository.save(productEntity);
        eventMulticaster.multicastEvent(new ProductCreatedEvent(productEntity));
        return productMapper.productEntityToProductRecord(productEntity);
    }

    public void updateProduct(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Async
    @SneakyThrows
    public void assignProductsTo(CatalogEntity catalogEntity) {
        Thread.sleep(3000);
        log.info("Assigning Products to {}", catalogEntity);
        productRepository.findAll().forEach(product -> {
            product.addCatalog(catalogEntity);
            productRepository.save(product);
        });
    }

    public List<ProductRecord> getProductsOnSale() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productMerchandising.getProductsOnSale(productEntities).stream()
            .map(productMapper::productEntityToProductRecord)
            .toList();
    }
}
