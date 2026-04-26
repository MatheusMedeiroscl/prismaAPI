package com.medeiros.prisma_api.domains.product;


import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String category,
        BigDecimal costPrice,
        BigDecimal salePrice) {

    public ProductResponseDTO(Product product){
        this(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getCostPrice(),
                product.getSalePrice()
        );
    }
}
