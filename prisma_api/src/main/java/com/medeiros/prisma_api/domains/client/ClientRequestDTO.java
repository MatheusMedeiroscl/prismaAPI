package com.medeiros.prisma_api.domains.client;

import jakarta.validation.constraints.NotEmpty;

public record ClientRequestDTO(
        @NotEmpty
        String storeName,
        @NotEmpty
        String owner,
        String cnpj,
        String address
) {
}
