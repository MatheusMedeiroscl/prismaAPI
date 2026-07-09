package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.domains.product.ProductRequestDTO;
import com.medeiros.prisma_api.domains.product.ProductResponseDTO;
import com.medeiros.prisma_api.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll (){
        List<ProductResponseDTO> listOfProducts = this.service.findAll();
        return ResponseEntity.ok(listOfProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById (@PathVariable Long id){
        ProductResponseDTO product = this.service.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO dto){
        ProductResponseDTO product = this.service.create(dto);
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody @Valid ProductRequestDTO dto, @PathVariable Long id){
        ProductResponseDTO product = this.service.update(dto, id);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.service.delete(id);
    }

    @GetMapping("/export")
    public void exportSalesForClient(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment: filename=products.xlsx");
        this.service.exportProducts(response.getOutputStream());
    }

}
