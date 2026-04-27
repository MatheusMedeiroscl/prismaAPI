package com.medeiros.prisma_api.domains.Movements;

import jakarta.validation.constraints.NotNull;

public record MovementRequestDTO(
        @NotNull
        Long idProduct,
        @NotNull
        MovementType type,
        @NotNull
        Integer quantity
) {
}
