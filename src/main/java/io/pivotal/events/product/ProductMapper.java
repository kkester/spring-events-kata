package io.pivotal.events.product;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ProductMapper {
    ProductRecord productEntityToProductRecord(ProductEntity productEntity);
    ProductEntity newProductRecordToProductEntity(NewProductRecord productRecord);
}
