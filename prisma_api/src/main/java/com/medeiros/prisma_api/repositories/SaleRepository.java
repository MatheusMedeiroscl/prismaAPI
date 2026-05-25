package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.sales.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
