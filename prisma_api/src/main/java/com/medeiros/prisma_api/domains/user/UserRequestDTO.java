package com.medeiros.prisma_api.domains.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        @NotBlank
        String userName,
        @NotNull
        UserPermission permission,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
