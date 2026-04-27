package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.Movements.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementsRepository extends JpaRepository<Movement, Long> {
}
