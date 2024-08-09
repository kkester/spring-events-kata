package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.ProductService;
import io.pivotal.events.util.FutureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogService {

    private final ProductService productService;
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;
    private final Executor taskExecutor;

    public CatalogRecord getCatalog(Long catalogId) {
        log.info("Getting catalog for {}", catalogId);
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId).orElseThrow();
        List<Future<ProductRecord>> productFutures = catalogEntity.getProducts().stream()
            .map(this::toProductRecord)
            .toList();
        List<ProductRecord> products = productFutures.stream()
            .map(FutureUtil::get)
            .toList();
        return new CatalogRecord(
            catalogEntity.getId(),
            catalogEntity.getName(),
            catalogEntity.getDescription(),
            catalogEntity.getStartDate(),
            catalogEntity.getEndDate(),
            products
        );
    }

    private Future<ProductRecord> toProductRecord(ProductEntity productEntity) {
        return CompletableFuture.supplyAsync(() -> productService.getProductById(productEntity.getId()), taskExecutor);
    }

    public void saveCatalog(CatalogRecord catalogRecord) {
        log.info("Creating catalog {}", catalogRecord);
        CatalogEntity catalogEntity = catalogMapper.catalogRecordToCatalogEntity(catalogRecord);
        catalogRepository.save(catalogEntity);
        productService.assignProductsTo(catalogEntity);
    }
}
