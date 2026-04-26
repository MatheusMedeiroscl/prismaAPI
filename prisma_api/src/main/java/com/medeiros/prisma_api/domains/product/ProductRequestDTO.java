package com.medeiros.prisma_api.domains.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String category,
        @NotNull
        BigDecimal costPrice,
        @NotNull
        BigDecimal salePrice

) {
}
