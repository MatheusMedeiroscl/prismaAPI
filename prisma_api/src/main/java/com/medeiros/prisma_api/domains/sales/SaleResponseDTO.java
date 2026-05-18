package com.medeiros.prisma_api.domains.sales;

import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public record SaleResponseDTO(
        Long id, List<SaleItemResponseDTO> items, Integer totalQuantity, BigDecimal totalCash
) {

    public SaleResponseDTO(Sale sale){
        this(
                sale.getId(),
                sale.getItems().stream()
                        .map(SaleItemResponseDTO::new)
                        .toList(),
                sale.getTotalQuantity(),
                sale.getTotalCash()
        );
    }
}
