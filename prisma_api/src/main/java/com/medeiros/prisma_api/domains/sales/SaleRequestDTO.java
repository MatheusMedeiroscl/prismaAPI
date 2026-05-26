package com.medeiros.prisma_api.domains.sales;

import com.medeiros.prisma_api.domains.saleitems.SaleItemRequestDTO;
import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;

import java.util.Date;
import java.util.List;

public record SaleRequestDTO(
        List<SaleItemRequestDTO> items,
        Long clientID,
        SaleStatus status,
        PaymentMethod method,
        Date dueDate
) {


}
