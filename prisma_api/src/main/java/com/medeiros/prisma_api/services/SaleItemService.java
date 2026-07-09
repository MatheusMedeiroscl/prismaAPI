package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.Movements.MovementRequestDTO;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.saleitems.SaleItem;
import com.medeiros.prisma_api.domains.saleitems.SaleItemRequestDTO;
import com.medeiros.prisma_api.domains.saleitems.SaleItemResponseDTO;
import com.medeiros.prisma_api.domains.sales.Sale;
import com.medeiros.prisma_api.domains.sales.SaleStatus;
import com.medeiros.prisma_api.domains.stocks.Stock;
import com.medeiros.prisma_api.repositories.ProductRepository;
import com.medeiros.prisma_api.repositories.SaleItemRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
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
        Product product = this.productRepository.findById(saleItem.getProduct().getId()).orElseThrow(() ->
                new RuntimeException(
                        "PRODUCT NOT FOUND" + saleItem.getProduct().getId()
                )
        );
        MovementRequestDTO movement = new MovementRequestDTO(
                saleItem.getProduct().getId(), // <-- aqui também
                MovementType.CANCEL,
                saleItem.getQuantity()
        );
        this.movementService.create(movement);
        saleItemRepository.delete(saleItem);
    }

    public void exportSaleItems(OutputStream outputStream) throws IOException {
        final List<SaleItem> saleItems = this.saleItemRepository.findAll();

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("saleItems");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Produto");
        header.createCell(2).setCellValue("Categoria");
        header.createCell(3).setCellValue("Quantidade");
        header.createCell(4).setCellValue("Total");
        header.createCell(5).setCellValue("Data");
        header.createCell(6).setCellValue("Status");

        int rowNum = 1;
        for(SaleItem saleItem: saleItems){
            final Row row = sheet.createRow(rowNum++);
            Double total = saleItem.getProduct().getCostPrice()
                    .multiply(BigDecimal.valueOf(saleItem.getQuantity()))
                    .doubleValue();

            row.createCell(0).setCellValue(saleItem.getId());
            row.createCell(1).setCellValue(saleItem.getProduct().getName());
            row.createCell(2).setCellValue(saleItem.getProduct().getCategory());
            row.createCell(3).setCellValue(saleItem.getQuantity());
            row.createCell(4).setCellValue(total);
            row.createCell(5).setCellValue(saleItem.getCreatedAt());
            row.createCell(6).setCellValue(saleItem.getSale().getSaleStatus().name());
        }
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


}
