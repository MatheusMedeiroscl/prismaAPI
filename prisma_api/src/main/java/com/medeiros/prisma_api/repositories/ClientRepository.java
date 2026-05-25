package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
