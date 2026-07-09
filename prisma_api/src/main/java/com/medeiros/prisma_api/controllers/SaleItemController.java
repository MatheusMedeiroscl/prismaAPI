package com.medeiros.prisma_api.controllers;

import com.medeiros.prisma_api.services.SaleItemService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("saleItems")
public class SaleItemController {

    private final SaleItemService service;

    public SaleItemController(SaleItemService service) {
        this.service = service;
    }

    @GetMapping("/export")
    public void exportSalesForClient(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment: filename=sale-clients.xlsx");
        this.service.exportSaleItems(response.getOutputStream());
    }

}
