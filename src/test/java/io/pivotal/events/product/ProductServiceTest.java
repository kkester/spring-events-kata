package io.pivotal.events.product;

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
        await().untilAsserted(() -> verify(productRepository, times(2)).save(captor.capture()));
        assertThat(captor.getAllValues().get(1).getInventoryStatus()).isNotNull();
    }

    @Test
    void getProductsOnSale() {
        ProductEntity product1 = createProductEntity();
        ProductEntity product2 = createProductEntity();
        ProductEntity product3 = createProductEntity();
        ProductEntity product4 = createProductEntity();

        List<ProductEntity> products = List.of(product1, product2, product3, product4);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductRecord> productsOnSale = productService.getProductsOnSale();

        assertThat(productsOnSale).hasSizeBetween(0,4);
    }
}