package io.pivotal.events.product;

import io.pivotal.events.product.inventory.InventoryStatus;
import io.pivotal.events.product.inventory.InventoryStatusEntity;
import io.pivotal.events.product.inventory.InventoryStatusRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T16:56:40-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity productRecordToProductEntity(ProductRecord productRecord) {
        if ( productRecord == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder productEntity = ProductEntity.builder();

        productEntity.id( productRecord.id() );
        productEntity.name( productRecord.name() );
        productEntity.description( productRecord.description() );
        productEntity.sku( productRecord.sku() );
        productEntity.inventoryStatus( inventoryStatusRecordToInventoryStatusEntity( productRecord.inventoryStatus() ) );

        return productEntity.build();
    }

    @Override
    public ProductRecord productEntityToProductRecord(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String sku = null;
        InventoryStatusRecord inventoryStatus = null;

        id = productEntity.getId();
        name = productEntity.getName();
        description = productEntity.getDescription();
        sku = productEntity.getSku();
        inventoryStatus = inventoryStatusEntityToInventoryStatusRecord( productEntity.getInventoryStatus() );

        ProductRecord productRecord = new ProductRecord( id, name, description, sku, inventoryStatus );

        return productRecord;
    }

    @Override
    public ProductEntity newProductRecordToProductEntity(NewProductRecord productRecord) {
        if ( productRecord == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder productEntity = ProductEntity.builder();

        productEntity.name( productRecord.name() );
        productEntity.description( productRecord.description() );
        productEntity.sku( productRecord.sku() );

        return productEntity.build();
    }

    protected InventoryStatusEntity inventoryStatusRecordToInventoryStatusEntity(InventoryStatusRecord inventoryStatusRecord) {
        if ( inventoryStatusRecord == null ) {
            return null;
        }

        InventoryStatusEntity.InventoryStatusEntityBuilder inventoryStatusEntity = InventoryStatusEntity.builder();

        inventoryStatusEntity.status( inventoryStatusRecord.status() );
        inventoryStatusEntity.quantity( inventoryStatusRecord.quantity() );

        return inventoryStatusEntity.build();
    }

    protected InventoryStatusRecord inventoryStatusEntityToInventoryStatusRecord(InventoryStatusEntity inventoryStatusEntity) {
        if ( inventoryStatusEntity == null ) {
            return null;
        }

        InventoryStatus status = null;
        Integer quantity = null;

        status = inventoryStatusEntity.getStatus();
        quantity = inventoryStatusEntity.getQuantity();

        InventoryStatusRecord inventoryStatusRecord = new InventoryStatusRecord( status, quantity );

        return inventoryStatusRecord;
    }
}
