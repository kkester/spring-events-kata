package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CATALOG")
public class CatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATALOG_ID")
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "CATALOG_PRODUCT", joinColumns = @JoinColumn(name = "CATALOG_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Builder.Default
    private List<ProductEntity> products = new ArrayList<>();

    public void addProduct(ProductEntity productEntity) {
        this.products.add(productEntity);
        productEntity.getCatalogs().add(this);
    }
}
