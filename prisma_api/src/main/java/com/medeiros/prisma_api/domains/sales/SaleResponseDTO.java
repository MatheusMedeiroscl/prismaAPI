package com.medeiros.prisma_api.domains.sales;

import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record SaleResponseDTO(
        Long id,
        String client,
        LocalDate creatAt,
        PaymentMethod paymentMethod,
        SaleStatus saleStatus,
        Date dueDate,
        Integer totalQuantity,
        BigDecimal totalCash,
        List<SaleItemResponseDTO> items
) {

    public SaleResponseDTO(Sale sale){
        this(
                sale.getId(),
                sale.getClient().getStoreName(),
                sale.getCreateAt(),
                sale.getPaymentMethod(),
                sale.getSaleStatus(),
                sale.getDueDate(),
                sale.getTotalQuantity(),
                sale.getTotalCash(),
                sale.getItems().stream()
                        .map(SaleItemResponseDTO::new)
                        .toList()

        );
    }
}
