package io.pivotal.events.product;

import io.pivotal.events.product.inventory.InventoryStatusEntity;
import io.pivotal.events.product.inventory.InventoryStatusRecord;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {

    public static ProductEntity createProductEntity() {
        return ProductEntity.builder()
            .id(1L)
            .name("namer")
            .description("My Product has a name")
            .sku("ABC-12345-S-BL")
            .createdDate(LocalDateTime.now())
            .build();
    }

    public static ProductRecord createProductRecord() {
        return new ProductRecord(1L, "namer", "My Product has a name", "ABC-12345-S-BL", LocalDateTime.now(), null);
    }

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Test
    void createProduct() {
        NewProductRecord product = new NewProductRecord(
            "Gustosh",
            "Mystery Thing",
            "b32-wtf"
        );

        productService.createProduct(product);

        List<ProductEntity> productEntities = productRepository.findAll();
        assertThat(productEntities).hasSize(1)
            .first()
            .extracting("inventoryStatus")
            .isEqualTo(new InventoryStatusEntity());
    }
}