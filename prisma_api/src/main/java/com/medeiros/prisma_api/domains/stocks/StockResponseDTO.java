package com.medeiros.prisma_api.domains.stocks;

import com.medeiros.prisma_api.domains.product.Product;

import java.math.BigDecimal;

public record StockResponseDTO(String name , Integer Quantity, BigDecimal costPrice, StockStatus status) {

    public StockResponseDTO(Stock stock){
        this(
                stock.getProduct().getName(),
                stock.getQuantity(),
                stock.getProduct().getCostPrice(),
                stock.getStatus()
        );
    }
}
