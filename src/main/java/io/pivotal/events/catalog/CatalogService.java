package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductService productService;
    private final CatalogRepository catalogRepository;
    private final CatalogMapper catalogMapper;

    public CatalogRecord getCatalog(Long catalogId) {
        CatalogEntity catalogEntity = catalogRepository.findById(catalogId).orElseThrow();
        List<ProductRecord> products = Collections.emptyList();
        return new CatalogRecord(
            catalogEntity.getId(),
            catalogEntity.getName(),
            catalogEntity.getDescription(),
            catalogEntity.getStartDate(),
            catalogEntity.getEndDate(),
            catalogEntity.getCreatedDate(),
            products
        );
    }

    public void saveCatalog(CatalogRecord catalogRecord) {
        CatalogEntity catalogEntity = catalogMapper.catalogRecordToCatalogEntity(catalogRecord);
        catalogRepository.save(catalogEntity);
    }
}
