package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogService {

    private final ProductService productService;
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    public CatalogRecord getCatalog(Long catalogId) {
        log.info("Getting catalog for {}", catalogId);
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId).orElseThrow();
        // TODO: Convert each ProductEntity concurrently
        List<ProductRecord> products = catalogEntity.getProducts().stream()
            .map(this::toProductRecord)
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

    private ProductRecord toProductRecord(ProductEntity productEntity) {
        return productService.getProductById(productEntity.getId());
    }

    public void saveCatalog(CatalogRecord catalogRecord) {
        log.info("Creating catalog {}", catalogRecord);
        CatalogEntity catalogEntity = catalogMapper.catalogRecordToCatalogEntity(catalogRecord);
        catalogRepository.save(catalogEntity);
        // TODO: Assign products to new catalog asynchronously
        productService.assignProductsTo(catalogEntity);
    }
}
