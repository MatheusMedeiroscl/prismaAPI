package com.medeiros.prisma_api.domains.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
