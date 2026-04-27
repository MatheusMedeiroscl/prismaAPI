package com.medeiros.prisma_api.domains.Movements;

public record MovementResponseDTO(String product, Integer quantity, MovementType movementType) {

    public MovementResponseDTO(Movement movement){
        this(
                movement.getProduct().getName(),
                movement.getQuantity(),
                movement.getMovementType()
        );
    }
}
