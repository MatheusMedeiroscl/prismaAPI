package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.Movements.Movement;
import com.medeiros.prisma_api.domains.Movements.MovementType;
import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.stocks.Stock;
import com.medeiros.prisma_api.domains.stocks.StockRequestDTO;
import com.medeiros.prisma_api.domains.stocks.StockResponseDTO;
import com.medeiros.prisma_api.domains.stocks.StockStatus;
import com.medeiros.prisma_api.repositories.MovementsRepository;
import com.medeiros.prisma_api.repositories.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    private final StockRepository repository;
    private final MovementsRepository movementsRepository;
    public static Logger log =  LoggerFactory.getLogger(StockService.class);

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
        log.info("MOVEMENT TYPE RECEBIDO:" + status);

        if (stock == null || status == MovementType.ORDER) {
            stock = new Stock(product, quantity, StockStatus.from(status));
            log.info("MOVEMENT TYPE ENVIADO:" + StockStatus.from(status));
        } else {
            stock.setQuantity(stock.getQuantity() + quantity);
            stock.setStatus(StockStatus.AVAILABLE);
        }

        repository.save(stock);

    }

    public void removeQuantity(Product product, Integer quantity,  MovementType status) {
        Stock stock = repository.findByProductId(product.getId());
        log.info("MOVEMENT TYPE RECEBIDO:" + status);

        if (stock == null) {
            stock = new Stock(product, quantity, StockStatus.from(status));
            log.info("MOVEMENT TYPE ENVIADO:" + StockStatus.from(status));

        } else {
            stock.setQuantity(stock.getQuantity() - quantity);
        }

        repository.save(stock);
    }


    public StockResponseDTO confirm(Long id) {
        Stock stock = this.repository.findById(id).orElseThrow(() ->
                new RuntimeException("STOCK LINE NOT FOUNDED")
        );

        Stock stockAvailable = this.repository.findByProductIdAndStatus(stock.getProduct().getId(), StockStatus.AVAILABLE);
        if (stockAvailable != null){
            stockAvailable.setQuantity(stock.getQuantity() + stockAvailable.getQuantity());
            repository.save(stockAvailable);

            this.repository.delete(stock);
        }else {
            stock.setStatus(StockStatus.AVAILABLE);
            repository.save(stock);
        }


        Movement movement = new Movement(stock.getProduct(), stock.getQuantity(), MovementType.IN);
        movementsRepository.save(movement);

        return new StockResponseDTO(stock);
    };


    public StockResponseDTO updateOrder(Long id, StockRequestDTO dto){
        Stock stock = this.repository.findById(id).orElseThrow(() ->
                new RuntimeException("STOCK LINE NOT FOUNDED")
        );

        Integer difference = dto.quantity() - stock.getQuantity();
        MovementType type = difference > 0 ? MovementType.IN : MovementType.ADJUSTMENT;

        stock.setQuantity(dto.quantity());
        repository.save(stock);

        Movement movement = new Movement(stock.getProduct(), Math.abs(difference), type);
        movementsRepository.save(movement);
        return new StockResponseDTO(stock);
    };
}
