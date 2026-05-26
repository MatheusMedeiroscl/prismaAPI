package com.medeiros.prisma_api.domains.sales;

import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;
    @Column(name = "total_cash", nullable = false)
    private BigDecimal totalCash;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "creatAt", nullable = false)
    private LocalDate createAt = LocalDate.now();

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "sale_status", nullable = false)
    private SaleStatus saleStatus;

    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    public Sale(SaleRequestDTO dto) {

        this.dueDate = dto.dueDate();
        this.saleStatus = dto.status();
        this.paymentMethod = dto.method();
    }
}
