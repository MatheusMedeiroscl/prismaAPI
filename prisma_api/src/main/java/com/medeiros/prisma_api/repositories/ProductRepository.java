package com.medeiros.prisma_api.repositories;

import com.medeiros.prisma_api.domains.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
