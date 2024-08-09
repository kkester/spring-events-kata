package io.pivotal.events.catalog;

import io.pivotal.events.catalog.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity,Long> {
}
