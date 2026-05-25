package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.client.ClientRequestDTO;
import com.medeiros.prisma_api.domains.client.ClientResponseDTO;
import com.medeiros.prisma_api.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<ClientResponseDTO> findAll() {
        return this.repository.findAll()
                .stream().map(ClientResponseDTO:: new).toList();
    }

    public ClientResponseDTO findById(Long id) {
        Client client =   this.repository.findById(id).orElseThrow(() ->
                new RuntimeException(
                       "CLIENT NOT FOUNDED BY ID " + id
                )
        );

        return new ClientResponseDTO(client);
    }

    public ClientResponseDTO create(ClientRequestDTO dto){
        Client client = new Client(dto);
        this.repository.save(client);

        return new ClientResponseDTO(client);
    }

    public ClientResponseDTO update(Long id, ClientRequestDTO dto){
        Client client =   this.repository.findById(id).orElseThrow(() ->
                new RuntimeException()
        );

        if (dto.storeName() != null) {client.setStoreName(dto.storeName());}
        if (dto.owner() != null) {client.setOwner(dto.owner());}
        if (dto.cnpj() != null) {client.setCnpj(dto.cnpj());}
        if (dto.address() != null) {client.setAddress(dto.address());}

        this.repository.save(client);
        return new ClientResponseDTO(client);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
