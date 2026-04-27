package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.stocks.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByProductId(Long id);
}
