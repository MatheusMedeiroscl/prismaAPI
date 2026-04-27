package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.domains.stocks.StockRequestDTO;
import com.medeiros.prisma_api.domains.stocks.StockResponseDTO;
import com.medeiros.prisma_api.services.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> getAll (){
        List<StockResponseDTO> listOfStock = this.service.findAll();
        return ResponseEntity.ok(listOfStock);
    }

    @PostMapping("/{id}")
    public ResponseEntity<StockResponseDTO> ajustOrder (@PathVariable Long id, @RequestBody @Valid StockRequestDTO dto){

        if (dto.quantity() != null) service.updateOrder(id, dto);
        if (dto.status() != null) service.updateOrder(id, dto);

        StockResponseDTO responseDTO = this.service.findById(id);
        return ResponseEntity.ok().body(responseDTO);
    };
}
