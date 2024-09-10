package io.pivotal.events.product;

import io.pivotal.events.product.sse.ProductSseEmitter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    public static ProductEntity createProductEntity() {
        return ProductEntity.builder()
            .id(1L)
            .name("namer")
            .description("My Product has a name")
            .sku("ABC-12345-S-BL")
            .build();
    }

    public static ProductRecord createProductRecord(Long id) {
        return new ProductRecord(
            id,
            "namer",
            "My Product has a name",
            "ABC-12345-S-BL",
            null);
    }

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductSseEmitter productSseEmitter;

    @Test
    void createProduct() {
        NewProductRecord product = new NewProductRecord(
            "Gustosh",
            "Mystery Thing",
            "b32-wtf"
        );
        ProductEntity productEntity = ProductEntity.builder()
                .id(1L)
                .name(product.name())
                .sku(product.sku())
                .description(product.description())
                .build();
        when(productRepository.save(any())).thenReturn(productEntity);

        productService.createProduct(product);

        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, atMost(2)).save(captor.capture());
        assertThat(captor.getValue().getInventoryStatus()).isNull();
        await().until(() -> captor.getValue().getInventoryStatus() != null);
        await().untilAsserted(() -> verify(productSseEmitter).emitProductUpdated(new ProductRecord(productEntity.getId(), product.name(), product.description(), product.sku(), null)));
    }

    @Test
    void getProductsOnSale() {
        ProductEntity product1 = createProductEntity().toBuilder().id(1L).build();
        ProductEntity product2 = createProductEntity().toBuilder().id(2L).build();
        ProductEntity product3 = createProductEntity().toBuilder().id(3L).build();
        ProductEntity product4 = createProductEntity().toBuilder().id(4L).build();
        when(productRepository.findAll()).thenReturn(List.of(product1,product2,product3,product4));

        List<ProductRecord> productsOnSale = productService.getProductsOnSale();

        assertThat(productsOnSale).hasSizeBetween(0,4);
    }
}