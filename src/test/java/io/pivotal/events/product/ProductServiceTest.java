package io.pivotal.events.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pivotal.events.product.inventory.InventoryStatusRecord;
import io.pivotal.events.product.sse.ProductSseEmitter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.Message;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.pivotal.events.product.inventory.InventoryStatus.IN_STOCK;
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

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createProduct() throws Exception {
        NewProductRecord product = new NewProductRecord(
                "Gustosh",
                "Mystery Thing",
                "b32-wtf"
        );
        when(productRepository.save(any())).thenAnswer(input -> {
            ProductEntity result = input.getArgument(0);
            result.setId(1L);
            return result;
        });

        productService.createProduct(product);

        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
        verify(productRepository, atMost(2)).save(captor.capture());
        assertThat(captor.getValue().getInventoryStatus()).isNull();
        await().until(() -> captor.getValue().getInventoryStatus() != null);

        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        ArgumentCaptor<byte[]> patternCaptor = ArgumentCaptor.forClass(byte[].class);
        await().untilAsserted(() -> verify(productSseEmitter).onMessage(messageCaptor.capture(), patternCaptor.capture()));

        ProductRecord expectedProductRecord = new ProductRecord(1L, product.name(), product.description(), product.sku(), new InventoryStatusRecord(IN_STOCK, 100));
        assertThat(objectMapper.readValue(messageCaptor.getValue().getBody(), ProductRecord.class)).isEqualTo(expectedProductRecord);
        assertThat(patternCaptor.getValue()).isEqualTo("product.update".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void getProductsOnSale() {
        ProductEntity product1 = createProductEntity().toBuilder().id(1L).build();
        ProductEntity product2 = createProductEntity().toBuilder().id(2L).build();
        ProductEntity product3 = createProductEntity().toBuilder().id(3L).build();
        ProductEntity product4 = createProductEntity().toBuilder().id(4L).build();
        when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3, product4));

        List<ProductRecord> productsOnSale = productService.getProductsOnSale();

        assertThat(productsOnSale).hasSizeBetween(0, 4);
    }
}