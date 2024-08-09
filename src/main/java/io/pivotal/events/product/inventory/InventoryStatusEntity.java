package io.pivotal.events.product.inventory;

import io.pivotal.events.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "INVENTORY_STATUS")
public class InventoryStatusEntity {
    @Id
    private Long productId;
    @OneToOne
    @JoinColumn(name = "product_id")
    @MapsId
    private ProductEntity product;
    private InventoryStatus status;
    private Integer quantity;
}
