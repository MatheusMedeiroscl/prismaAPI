package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.client.ClientRequestDTO;
import com.medeiros.prisma_api.domains.client.ClientResponseDTO;
import com.medeiros.prisma_api.services.ClientService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll (){
        List<ClientResponseDTO> list = this.service.findAll();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getById (@PathVariable Long id){
        return ResponseEntity.ok().body(this.service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create (@RequestBody ClientRequestDTO clientRequestDTO){
        return ResponseEntity.ok().body(this.service.create(clientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update (@PathVariable Long id ,@RequestBody ClientRequestDTO clientRequestDTO){
        return ResponseEntity.ok().body(this.service.update(id, clientRequestDTO));
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable Long id){
        this.service.delete(id);
    }

    @GetMapping("/export")
    public void exportSalesForClient(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment: filename=clients.xlsx");
        this.service.exportClient(response.getOutputStream());
    }


}
