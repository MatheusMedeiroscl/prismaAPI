package com.medeiros.prisma_api.services;

import com.medeiros.prisma_api.domains.product.Product;
import com.medeiros.prisma_api.domains.product.ProductRequestDTO;
import com.medeiros.prisma_api.domains.product.ProductResponseDTO;
import com.medeiros.prisma_api.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseDTO> findAll(){
        return this.repository.findAll()
                .stream().map(ProductResponseDTO:: new).toList();
    }

    public ProductResponseDTO findById(Long id){
        Product product = this.repository.findById(id).orElseThrow(() -> new RuntimeException(
                "PRODUCT NOT FOUNDED BY ID [ " + id + " ]"
        ));

        return new ProductResponseDTO(product);
    }


    public ProductResponseDTO create(ProductRequestDTO dto){
        Product product = new Product(dto);

        this.repository.save(product);
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO update(ProductRequestDTO dto, Long id){
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException(
                "PRODUCT NOT FOUNDED BY ID [ " + id + " ]"
        ));

        if (dto.name() != null) product.setName(dto.name());
        if (dto.category() != null) product.setCategory(dto.category());
        if (dto.costPrice() != null) product.setCostPrice(dto.costPrice());
        if (dto.salePrice() != null) product.setSalePrice(dto.salePrice());

        this.repository.save(product);
        return new ProductResponseDTO(product);
    }
}
