package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import com.medeiros.prisma_api.domains.saleitems.SaleItemRequestDTO;
import com.medeiros.prisma_api.domains.sales.Sale;
import com.medeiros.prisma_api.domains.sales.SaleRequestDTO;
import com.medeiros.prisma_api.domains.sales.SaleResponseDTO;
import com.medeiros.prisma_api.repositories.ClientRepository;
import com.medeiros.prisma_api.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemService saleItemService;
    private final ClientRepository clientRepository;


    public SaleService(SaleRepository saleRepository, SaleItemService saleItemService, ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.saleItemService = saleItemService;
        this.clientRepository = clientRepository;
    }

    public List<SaleResponseDTO> findAll() {
        List<SaleResponseDTO> sales = this.saleRepository.findAll()
                .stream().map(SaleResponseDTO:: new).toList();

        return sales;
    }

    public SaleResponseDTO findById(Long id) {
        Sale sale = this.saleRepository.findById(id).orElseThrow(() ->
            new RuntimeException(
                    "SALE NOT FOUND " +  id
            )
        );
            return  new SaleResponseDTO(sale);
    }

    public SaleResponseDTO create (SaleRequestDTO dto) {
        Client client =   this.clientRepository.findById(dto.clientID()).orElseThrow(() ->
                new RuntimeException(
                        "CLIENT NOT FOUNDED BY ID " + dto.clientID()
                )
        );

        Sale sale = new Sale(dto);
        sale.setClient(client);

        List<SaleItem> items = new ArrayList<>();
        Integer totalQuantity = 0;
        BigDecimal totalInCash = BigDecimal.ZERO;

        for (SaleItemRequestDTO item: dto.items()){
            SaleItem request = this.saleItemService.build(item, dto.status());

            request.setSale(sale);

            totalQuantity += request.getQuantity();

            BigDecimal itemTotal = request.getSalePrice()
                    .multiply(BigDecimal.valueOf(request.getQuantity()));

            totalInCash = totalInCash.add(itemTotal);
            items.add(request);
        };

        sale.setItems(items);
        sale.setTotalQuantity(totalQuantity);
        sale.setTotalCash(totalInCash);

        this.saleRepository.save(sale);
        return new SaleResponseDTO(sale);

    };

    public SaleResponseDTO update (Long id, SaleRequestDTO dto) {
        Sale sale = this.saleRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        "SALE NOT FOUND " +  id
                )
        );

        if (dto.items() != null) {
            sale.getItems().clear();

            Integer totalQuantity = 0;
            BigDecimal totalInCash = BigDecimal.ZERO;

            for (SaleItemRequestDTO item : dto.items()) {
                SaleItem request = this.saleItemService.build(item, dto.status());

                request.setSale(sale);
                totalQuantity += request.getQuantity();
                BigDecimal itemTotal = request.getSalePrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

                totalInCash = totalInCash.add(itemTotal);

                sale.getItems().add(request);

            };
            sale.setTotalQuantity(totalQuantity);
            sale.setTotalCash(totalInCash);
        }
        if (dto.clientID() != null) {sale.setClient(
                clientRepository.findById(dto.clientID()).orElseThrow(() ->
                        new RuntimeException(
                                "CLIENT NOT FOUNDED BY ID " + dto
                        )
                ));
        }
         if (dto.status() != null) {sale.setSaleStatus(dto.status());}
         if (dto.method() != null) {sale.setPaymentMethod(dto.method());}
         if (dto.dueDate() != null) {sale.setDueDate(dto.dueDate());}

        this.saleRepository.save(sale);
        return new SaleResponseDTO(sale);

    }
}
