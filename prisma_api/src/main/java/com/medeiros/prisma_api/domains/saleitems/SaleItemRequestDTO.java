package com.medeiros.prisma_api.domains.saleitems;

import com.medeiros.prisma_api.domains.sales.SaleStatus;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SaleItemRequestDTO(
        @NotNull
        Long productId,
        @NotNull
        Integer quantity,
        @NotNull
        BigDecimal salePrice
) {
        public SaleItemRequestDTO(SaleItem saleItem){
                this(saleItem.getProduct().getId(), saleItem.getQuantity(), saleItem.getSalePrice());
        }
}
