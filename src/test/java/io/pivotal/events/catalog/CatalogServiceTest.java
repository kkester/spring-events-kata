package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static io.pivotal.events.product.ProductServiceTest.createProductEntity;
import static io.pivotal.events.product.ProductServiceTest.createProductRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CatalogServiceTest {

    @Autowired
    CatalogService catalogService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CatalogRepository catalogRepository;

    private static CatalogEntity createCatalogEntity() {
        return CatalogEntity.builder()
            .id(1L)
            .name("namer")
            .description("My Catalog has a name")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusYears(1))
            .build();
    }

    private static CatalogRecord createCatalogRecord() {
        return new CatalogRecord(1L,
            "namer",
            "My Catalog has a name",
            LocalDate.now(),
            LocalDate.now().plusYears(1),
            List.of(createProductRecord())
        );
    }

    @Test
    void getCatalogWithProducts() {
        CatalogEntity catalogEntity = createCatalogEntity();
        ProductEntity productEntity = createProductEntity();
        catalogEntity.addProduct(productEntity);
        when(catalogRepository.findById(catalogEntity.getId())).thenReturn(Optional.of(catalogEntity));
        when(productRepository.findById(productEntity.getId())).thenReturn(Optional.of(productEntity));

        CatalogRecord catalogRecord = catalogService.getCatalog(catalogEntity.getId());

        CatalogRecord expectedCatalogRecord = createCatalogRecord();
        assertThat(catalogRecord).isEqualTo(expectedCatalogRecord);
    }

    @Test
    void createCatalog() {
        CatalogRecord catalogRecord = createCatalogRecord();
        ProductEntity productEntity = createProductEntity();
        when(productRepository.findAll()).thenReturn(List.of(productEntity));

        catalogService.saveCatalog(catalogRecord);

        verify(catalogRepository).save(any());
        await().until(()-> !productEntity.getCatalogs().isEmpty());
    }
}