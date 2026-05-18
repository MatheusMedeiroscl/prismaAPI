package com.medeiros.prisma_api.domains.saleitems;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record SaleItemResponseDTO(
        Long id, String product, String category, Integer quantity, BigDecimal salePrice, LocalDate createdAt) {
    public SaleItemResponseDTO(SaleItem saleItem){
        this(
                saleItem.getId(),
                saleItem.getProduct().getName(),
                saleItem.getProduct().getCategory(),
                saleItem.getQuantity(),
                saleItem.getSalePrice(),
                saleItem.getCreatedAt()
        );
    }
}
