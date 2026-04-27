package com.medeiros.prisma_api.domains.stocks;

import com.medeiros.prisma_api.domains.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Product product;


    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "status")
    private StockStatus status;

    public Stock(Product product, Integer quantity, StockStatus status) {
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

}
