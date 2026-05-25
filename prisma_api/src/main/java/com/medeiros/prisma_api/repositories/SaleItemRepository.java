package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
