package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.domains.Movements.MovementRequestDTO;
import com.medeiros.prisma_api.domains.Movements.MovementResponseDTO;
import com.medeiros.prisma_api.services.MovementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movement")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> getAll (){
        List<MovementResponseDTO> listOfMovements = this.service.findAll();

        return  ResponseEntity.ok(listOfMovements);
    }


    @PostMapping
    public ResponseEntity<MovementResponseDTO> create (@RequestBody @Valid MovementRequestDTO dto){
        MovementResponseDTO movement = this.service.create(dto);
        return ResponseEntity.ok().body(movement);
    }
}
