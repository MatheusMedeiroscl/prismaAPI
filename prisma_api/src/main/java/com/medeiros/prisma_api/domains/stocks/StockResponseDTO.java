package com.medeiros.prisma_api.domains.stocks;

import com.medeiros.prisma_api.domains.product.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record StockResponseDTO(Long id, String name , Integer Quantity, BigDecimal costPrice, StockStatus status, LocalDate createAt) {

    public StockResponseDTO(Stock stock){
        this(
                stock.getId(),
                stock.getProduct().getName(),
                stock.getQuantity(),
                stock.getProduct().getCostPrice(),
                stock.getStatus(),
                stock.getCreatedAt()
        );
    }
}
