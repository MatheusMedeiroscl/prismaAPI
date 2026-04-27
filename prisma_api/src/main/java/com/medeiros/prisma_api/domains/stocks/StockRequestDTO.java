package com.medeiros.prisma_api.domains.stocks;

public record StockRequestDTO(
        StockStatus status,
        Integer quantity
) {
}
