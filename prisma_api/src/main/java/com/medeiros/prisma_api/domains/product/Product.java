package com.medeiros.prisma_api.domains.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", unique = true, nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "cost_price", nullable = false)
    private BigDecimal costPrice;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    public Product(ProductRequestDTO dto) {
        this.name = dto.name();
        this.category = dto.category();
        this.costPrice = dto.costPrice();
        this.salePrice = dto.salePrice();
    }
}
