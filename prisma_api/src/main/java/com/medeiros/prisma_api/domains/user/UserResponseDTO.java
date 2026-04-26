package com.medeiros.prisma_api.domains.user;

public record UserResponseDTO(
        Long Id,
        String userName,
        UserPermission permission,
        String email
) {

    public UserResponseDTO(User user){
        this(user.getId(), user.getUsername(), user.getPermission(), user.getEmail());
    }
}
