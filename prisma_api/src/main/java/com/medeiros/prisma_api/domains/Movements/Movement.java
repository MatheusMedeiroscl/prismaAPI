package com.medeiros.prisma_api.domains.Movements;

import com.medeiros.prisma_api.domains.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock_movements")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "createAt")
    LocalDate createdAt = LocalDate.now();

    public Movement(MovementRequestDTO dto, Product product) {
        this.product = product;
        this.quantity = dto.quantity();
        this.movementType = dto.type();
    }

    public Movement(Product product, Integer quantity, MovementType movementType) {
        this.product = product;
        this.quantity = quantity;
        this.movementType = movementType;
    }




}
