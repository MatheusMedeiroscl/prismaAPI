package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.client.Client;
import com.medeiros.prisma_api.domains.client.ClientRequestDTO;
import com.medeiros.prisma_api.domains.client.ClientResponseDTO;
import com.medeiros.prisma_api.domains.stocks.Stock;
import com.medeiros.prisma_api.repositories.ClientRepository;
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
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<ClientResponseDTO> findAll() {
        return this.repository.findAll()
                .stream().map(ClientResponseDTO:: new).toList();
    }

    public ClientResponseDTO findById(Long id) {
        Client client =   this.repository.findById(id).orElseThrow(() ->
                new RuntimeException(
                       "CLIENT NOT FOUNDED BY ID " + id
                )
        );

        return new ClientResponseDTO(client);
    }

    public ClientResponseDTO create(ClientRequestDTO dto){
        Client client = new Client(dto);
        this.repository.save(client);

        return new ClientResponseDTO(client);
    }

    public ClientResponseDTO update(Long id, ClientRequestDTO dto){
        Client client =   this.repository.findById(id).orElseThrow(() ->
                new RuntimeException()
        );

        if (dto.storeName() != null) {client.setStoreName(dto.storeName());}
        if (dto.owner() != null) {client.setOwner(dto.owner());}
        if (dto.email() != null) {client.setEmail(dto.email());}
        if (dto.cnpj() != null) {client.setCnpj(dto.cnpj());}
        if (dto.address() != null) {client.setAddress(dto.address());}

        this.repository.save(client);
        return new ClientResponseDTO(client);
    }

    public void delete(Long id) {
        this.repository.deleteById(id);
    }


    public void exportClient(OutputStream outputStream) throws IOException {
        final List<Client> clients = this.repository.findAll();

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("clients");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Loja");
        header.createCell(2).setCellValue("Resposável");
        header.createCell(3).setCellValue("email");
        header.createCell(4).setCellValue("CNPJ");
        header.createCell(5).setCellValue("Endereço");

        int rowNum = 1;
        for(Client client: clients){
            final Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(client.getId());
            row.createCell(1).setCellValue(client.getStoreName());
            row.createCell(2).setCellValue(client.getOwner());
            row.createCell(3).setCellValue(client.getEmail());
            row.createCell(4).setCellValue(client.getCnpj());
            row.createCell(5).setCellValue(client.getAddress());

        }
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}
