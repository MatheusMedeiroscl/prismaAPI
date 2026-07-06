package com.medeiros.prisma_api.domains.Movements;

import jakarta.validation.constraints.NotNull;

public record MovementRequestDTO(
        @NotNull
        Long productId,
        @NotNull
        MovementType type,
        @NotNull
        Integer quantity
) {
}
