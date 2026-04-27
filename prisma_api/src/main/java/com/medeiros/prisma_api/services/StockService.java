package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.Movements.Movement;
import com.medeiros.prisma_api.domains.Movements.MovementRequestDTO;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.stocks.Stock;
import com.medeiros.prisma_api.domains.stocks.StockRequestDTO;
import com.medeiros.prisma_api.domains.stocks.StockResponseDTO;
import com.medeiros.prisma_api.domains.stocks.StockStatus;
import com.medeiros.prisma_api.repositories.MovementsRepository;
import com.medeiros.prisma_api.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private final StockRepository repository;
    private final MovementsRepository movementsRepository;

    public StockService(StockRepository repository, MovementsRepository movementsRepository) {
        this.repository = repository;
        this.movementsRepository = movementsRepository;
    }

    public List<StockResponseDTO> findAll (){
        return this.repository.findAll()
                .stream().map(StockResponseDTO:: new).toList();
    }

    public StockResponseDTO findById (Long id){
        Stock stock = repository.findById(id).orElseThrow(() ->
                new RuntimeException("STOCK LINE NOT FOUNDED")
        );
        return new StockResponseDTO(stock);
    }

    public void addQuantity(Product product, Integer quantity, MovementType status) {
        Stock stock = repository.findByProductId(product.getId());

        if (stock == null) {
            stock = new Stock(product, quantity, StockStatus.from(status));
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
        }

        repository.save(stock);

    }

    public void removeQuantity(Product product, Integer quantity,  MovementType status) {
        Stock stock = repository.findByProductId(product.getId());

        if (stock == null) {
            stock = new Stock(product, quantity, StockStatus.from(status));
        } else {
            stock.setQuantity(stock.getQuantity() - quantity);
        }

        repository.save(stock);
    }


    public void confirm(Long id) {
        Stock stock = this.repository.findById(id).orElseThrow(() ->
                new RuntimeException("STOCK LINE NOT FOUNDED")
        );

        stock.setStatus(StockStatus.AVAILABLE);
        repository.save(stock);

        Movement movement = new Movement(stock.getProduct(), stock.getQuantity(), MovementType.IN);
        movementsRepository.save(movement);
    };

    public void updateOrder(Long id, StockRequestDTO dto){
        Stock stock = this.repository.findById(id).orElseThrow(() ->
                new RuntimeException("STOCK LINE NOT FOUNDED")
        );

        Integer difference = dto.quantity() - stock.getQuantity();
        MovementType type = difference > 0 ? MovementType.IN : MovementType.ADJUSTMENT;

        stock.setQuantity(dto.quantity());
        repository.save(stock);

        Movement movement = new Movement(stock.getProduct(), difference, MovementType.IN);
        movementsRepository.save(movement);
    };
}
