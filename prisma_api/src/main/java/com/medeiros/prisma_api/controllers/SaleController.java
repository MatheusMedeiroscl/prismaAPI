package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.domains.sales.SaleRequestDTO;
import com.medeiros.prisma_api.domains.sales.SaleResponseDTO;
import com.medeiros.prisma_api.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {

    private final SaleService service;

    public SaleController(SaleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SaleResponseDTO>> getAll(){
        List<SaleResponseDTO> sales = service.findAll();

        return ResponseEntity.ok(sales);
    }

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@RequestBody SaleRequestDTO dto){
        return  ResponseEntity.ok(service.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<SaleResponseDTO> update(@PathVariable Long id, @RequestBody SaleRequestDTO dto){
        return ResponseEntity.ok(service.update(id,dto));
    }

}
