package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.Movements.MovementRequestDTO;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import com.medeiros.prisma_api.domains.saleitems.SaleItemRequestDTO;
import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;
import com.medeiros.prisma_api.domains.sales.Sale;
import com.medeiros.prisma_api.domains.sales.SaleStatus;
import com.medeiros.prisma_api.repositories.ProductRepository;
import com.medeiros.prisma_api.repositories.SaleItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final MovementService movementService;
    private final ProductRepository productRepository;

    public SaleItemService(SaleItemRepository saleItemRepository, MovementService movementService, ProductRepository productRepository) {
        this.saleItemRepository = saleItemRepository;
        this.movementService = movementService;
        this.productRepository = productRepository;
    }

    public SaleItem build(SaleItemRequestDTO dto, SaleStatus status) {
        Product product = this.productRepository.findById(dto.productId()).orElseThrow(() ->
                new RuntimeException(
                        "PRODUCT NOT FOUND" + dto.productId()
                )
        );

        MovementRequestDTO movement = new MovementRequestDTO(dto.productId(), MovementType.from(status), dto.quantity());
        this.movementService.create(movement);

        return new SaleItem(dto, product);
    }

    public void delete(SaleItem saleItem) {
        Product product = this.productRepository.findById(saleItem.getId()).orElseThrow(() ->
                new RuntimeException(
                        "PRODUCT NOT FOUND" + saleItem.getId()
                )
        );

        MovementRequestDTO movement = new MovementRequestDTO(saleItem.getId(), MovementType.CANCEL, saleItem.getQuantity());
        this.movementService.create(movement);

        saleItemRepository.delete(saleItem);
    }


}
