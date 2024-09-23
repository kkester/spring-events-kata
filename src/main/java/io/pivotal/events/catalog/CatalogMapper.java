package io.pivotal.events.catalog;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface CatalogMapper {
    CatalogEntity catalogRecordToCatalogEntity(CatalogRecord catalogRecord);
}
