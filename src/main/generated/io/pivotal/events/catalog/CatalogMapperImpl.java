package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.inventory.InventoryStatusEntity;
import io.pivotal.events.product.inventory.InventoryStatusRecord;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T16:31:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class CatalogMapperImpl implements CatalogMapper {

    @Override
    public CatalogEntity catalogRecordToCatalogEntity(CatalogRecord catalogRecord) {
        if ( catalogRecord == null ) {
            return null;
        }

        CatalogEntity.CatalogEntityBuilder catalogEntity = CatalogEntity.builder();

        catalogEntity.id( catalogRecord.id() );
        catalogEntity.name( catalogRecord.name() );
        catalogEntity.description( catalogRecord.description() );
        catalogEntity.startDate( catalogRecord.startDate() );
        catalogEntity.endDate( catalogRecord.endDate() );
        catalogEntity.products( productRecordListToProductEntityList( catalogRecord.products() ) );

        return catalogEntity.build();
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

    protected ProductEntity productRecordToProductEntity(ProductRecord productRecord) {
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

    protected List<ProductEntity> productRecordListToProductEntityList(List<ProductRecord> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductEntity> list1 = new ArrayList<ProductEntity>( list.size() );
        for ( ProductRecord productRecord : list ) {
            list1.add( productRecordToProductEntity( productRecord ) );
        }

        return list1;
    }
}
