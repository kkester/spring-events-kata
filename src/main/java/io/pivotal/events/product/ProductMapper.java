package io.pivotal.events.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface ProductMapper {
    ProductEntity productRecordToProductEntity(ProductRecord productRecord);
    ProductRecord productEntityToProductRecord(ProductEntity productEntity);
    ProductEntity newProductRecordToProductEntity(NewProductRecord productRecord);
}
