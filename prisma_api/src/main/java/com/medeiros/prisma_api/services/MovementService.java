package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.configs.security.TokenService;
import com.medeiros.prisma_api.domains.Movements.Movement;
import com.medeiros.prisma_api.domains.Movements.MovementRequestDTO;
import com.medeiros.prisma_api.domains.Movements.MovementResponseDTO;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.repositories.MovementsRepository;
import com.medeiros.prisma_api.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {
    private final MovementsRepository movementsRepository;
    private final StockService stockService;
    private final ProductRepository productRepository;
    public static Logger log =  LoggerFactory.getLogger(MovementService.class);

    public MovementService(MovementsRepository movementsRepository, StockService stockService, ProductRepository productRepository) {
        this.movementsRepository = movementsRepository;
        this.stockService = stockService;
        this.productRepository = productRepository;
    }


    private Product findProduct(Long id){
        return this.productRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "PRODUCT NOT FOUNDED BY ID [ " + id + " ]"
        ));
    }

    public List<MovementResponseDTO> findAll(){
        return movementsRepository.findAll().stream()
                .map(MovementResponseDTO :: new)
                .toList();
    }

    @Transactional
    public MovementResponseDTO create (MovementRequestDTO dto){
        Product product = findProduct(dto.idProduct());
        log.info(String.valueOf(dto.type()));

        // salva a movimentação
        Movement movement = new Movement(dto, product);
        this.movementsRepository.save(movement);


        if (dto.type() == MovementType.IN || dto.type() == MovementType.ORDER){
            this.stockService.addQuantity(product, dto.quantity(), dto.type());
        }else {
            this.stockService.removeQuantity(product, dto.quantity(), dto.type());
        }

        return new MovementResponseDTO(movement);
    }
}
