package com.medeiros.prisma_api.services;

import ch.qos.logback.core.OutputStreamAppender;
import com.medeiros.prisma_api.domains.Movements.Movement;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import com.medeiros.prisma_api.domains.saleitems.SaleItemRequestDTO;
import com.medeiros.prisma_api.domains.sales.Sale;
import com.medeiros.prisma_api.domains.sales.SaleRequestDTO;
import com.medeiros.prisma_api.domains.sales.SaleResponseDTO;
import com.medeiros.prisma_api.repositories.ClientRepository;
import com.medeiros.prisma_api.repositories.MovementsRepository;
import com.medeiros.prisma_api.repositories.SaleRepository;
import jakarta.servlet.ServletOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemService saleItemService;
    private final ClientRepository clientRepository;
    private final MovementsRepository movementsRepository;

    public SaleService(SaleRepository saleRepository, SaleItemService saleItemService, ClientRepository clientRepository, MovementsRepository movementsRepository) {
        this.saleRepository = saleRepository;
        this.saleItemService = saleItemService;
        this.clientRepository = clientRepository;
        this.movementsRepository = movementsRepository;
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

    public void exportSalesForClient(OutputStream outputStream) throws IOException {
        final List<Sale> sales = this.saleRepository.findAll();

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Sales-client");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Cliente");
        header.createCell(2).setCellValue("Dt Venda");
        header.createCell(3).setCellValue("Status");
        header.createCell(4).setCellValue("Pagamento");
        header.createCell(5).setCellValue("Quantidade");
        header.createCell(6).setCellValue("Total");
        header.createCell(7).setCellValue("Vencimento");

        int rowNum = 1;
        for (Sale sale: sales){
            final Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(sale.getId());
            row.createCell(1).setCellValue(sale.getClient().getStoreName());
            row.createCell(2).setCellValue(sale.getCreateAt());
            row.createCell(3).setCellValue(sale.getSaleStatus().name());
            row.createCell(4).setCellValue(sale.getPaymentMethod().name());
            row.createCell(5).setCellValue(sale.getTotalQuantity());
            row.createCell(6).setCellValue(sale.getTotalCash().doubleValue());
            row.createCell(7).setCellValue(sale.getDueDate());
        }

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
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

    public void delete (Long id) {
        Sale sale = this.saleRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        "SALE NOT FOUND " +  id
                )
        );

        for (SaleItem item: sale.getItems()) {
            this.saleItemService.delete(item);
        }

        this.saleRepository.delete(sale);

    }
}
