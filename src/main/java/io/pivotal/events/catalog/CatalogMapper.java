package io.pivotal.events.catalog;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface CatalogMapper {
    CatalogEntity catalogRecordToCatalogEntity(CatalogRecord catalogRecord);
}
