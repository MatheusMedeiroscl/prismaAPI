package com.medeiros.prisma_api.domains.client;

public record ClientResponseDTO(
        Long id, String storeName, String owner, String cnpj, String address
) {
    public ClientResponseDTO(Client client){
        this(
                client.getId(),
                client.getStoreName(),
                client.getOwner(),
                client.getCnpj(),
                client.getAddress()
        );
    }
}
