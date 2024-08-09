package io.pivotal.events.product;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
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
        return new ProductRecord(1L, "namer","My Product has a name","ABC-12345-S-BL",LocalDateTime.now(), null);
    }

}