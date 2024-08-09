package io.pivotal.events.product;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

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

    @Test
    void createProduct() {
        NewProductRecord product = new NewProductRecord(
            "Gustosh",
            "Mystery Thing",
            "b32-wtf"
        );

        productService.createProduct(product);

        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, atMost(2)).save(captor.capture());
        await().until(() -> captor.getValue().getInventoryStatus() != null);
    }
}