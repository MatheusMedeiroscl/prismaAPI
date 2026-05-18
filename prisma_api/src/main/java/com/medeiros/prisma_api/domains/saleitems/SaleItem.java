package com.medeiros.prisma_api.domains.saleitems;

import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.sales.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale_item")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @Column(name = "quatity", nullable = false)
    private Integer quantity;

    @Column(name = "sale_price",  nullable = false)
    private BigDecimal salePrice;

    @Column(name = "createAt")
    LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;



}
