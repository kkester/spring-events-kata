package io.pivotal.events.catalog;

import io.pivotal.events.product.ProductEntity;
import io.pivotal.events.product.ProductRecord;
import io.pivotal.events.product.inventory.InventoryStatusEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T11:43:24-0500",
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
        catalogEntity.createdDate( catalogRecord.createdDate() );
        catalogEntity.products( productRecordListToProductEntityList( catalogRecord.products() ) );

        return catalogEntity.build();
    }

    @Override
    public CatalogRecord catalogEntityToCatalogRecord(CatalogEntity catalogEntity) {
        if ( catalogEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalDateTime createdDate = null;
        List<ProductRecord> products = null;

        id = catalogEntity.getId();
        name = catalogEntity.getName();
        description = catalogEntity.getDescription();
        startDate = catalogEntity.getStartDate();
        endDate = catalogEntity.getEndDate();
        createdDate = catalogEntity.getCreatedDate();
        products = productEntityListToProductRecordList( catalogEntity.getProducts() );

        CatalogRecord catalogRecord = new CatalogRecord( id, name, description, startDate, endDate, createdDate, products );

        return catalogRecord;
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
        productEntity.createdDate( productRecord.createdDate() );
        productEntity.inventoryStatus( productRecord.inventoryStatus() );

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

    protected ProductRecord productEntityToProductRecord(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String sku = null;
        LocalDateTime createdDate = null;
        InventoryStatusEntity inventoryStatus = null;

        id = productEntity.getId();
        name = productEntity.getName();
        description = productEntity.getDescription();
        sku = productEntity.getSku();
        createdDate = productEntity.getCreatedDate();
        inventoryStatus = productEntity.getInventoryStatus();

        ProductRecord productRecord = new ProductRecord( id, name, description, sku, createdDate, inventoryStatus );

        return productRecord;
    }

    protected List<ProductRecord> productEntityListToProductRecordList(List<ProductEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductRecord> list1 = new ArrayList<ProductRecord>( list.size() );
        for ( ProductEntity productEntity : list ) {
            list1.add( productEntityToProductRecord( productEntity ) );
        }

        return list1;
    }
}
