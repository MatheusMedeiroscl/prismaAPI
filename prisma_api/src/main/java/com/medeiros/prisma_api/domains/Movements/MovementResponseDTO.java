package com.medeiros.prisma_api.domains.Movements;

import java.time.LocalDate;

public record MovementResponseDTO(String product, Integer quantity, MovementType movementType, LocalDate createAt) {

    public MovementResponseDTO(Movement movement){
        this(
                movement.getProduct().getName(),
                movement.getQuantity(),
                movement.getMovementType(),
                movement.getCreatedAt()
        );
    }
}
