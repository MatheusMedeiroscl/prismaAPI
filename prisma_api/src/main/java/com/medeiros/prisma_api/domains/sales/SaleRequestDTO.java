package com.medeiros.prisma_api.domains.sales;

import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;

import java.util.List;

public record SaleRequestDTO(
        List<SaleItemResponseDTO> items
) {

}
