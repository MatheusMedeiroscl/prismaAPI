package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.product.ProductRequestDTO;
import com.medeiros.prisma_api.domains.product.ProductResponseDTO;
import com.medeiros.prisma_api.domains.stocks.Stock;
import com.medeiros.prisma_api.repositories.ProductRepository;
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
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseDTO> findAll(){
        return this.repository.findAll()
                .stream().map(ProductResponseDTO:: new).toList();
    }

    public ProductResponseDTO findById(Long id){
        Product product = this.repository.findById(id).orElseThrow(() -> new RuntimeException(
                "PRODUCT NOT FOUNDED BY ID [ " + id + " ]"
        ));

        return new ProductResponseDTO(product);
    }


    public ProductResponseDTO create(ProductRequestDTO dto){
        Product product = new Product(dto);

        this.repository.save(product);
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO update(ProductRequestDTO dto, Long id){
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException(
                "PRODUCT NOT FOUNDED BY ID [ " + id + " ]"
        ));

        if (dto.name() != null) product.setName(dto.name());
        if (dto.category() != null) product.setCategory(dto.category());
        if (dto.costPrice() != null) product.setCostPrice(dto.costPrice());
        if (dto.salePrice() != null) product.setSalePrice(dto.salePrice());

        this.repository.save(product);
        return new ProductResponseDTO(product);
    }

    public void delete(Long id){
        this.repository.deleteById(id);
    }


    public void exportProducts(OutputStream outputStream) throws IOException {
        final List<Product> products = this.repository.findAll();

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("products");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Produto");
        header.createCell(2).setCellValue("Categoria");
        header.createCell(3).setCellValue("Preço Custo");
        header.createCell(4).setCellValue("Preço Venda");

        int rowNum = 1;
        for(Product product: products){
            final Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCategory());
            row.createCell(3).setCellValue(product.getCostPrice().doubleValue());
            row.createCell(4).setCellValue(product.getSalePrice().doubleValue());
        }
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}
