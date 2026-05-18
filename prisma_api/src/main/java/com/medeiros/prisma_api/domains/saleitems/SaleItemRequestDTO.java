package com.medeiros.prisma_api.domains.saleitems;

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
}
