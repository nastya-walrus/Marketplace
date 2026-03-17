package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto create(ProductDto productDto) {

        ProductEntity entity = new ProductEntity();
        entity.setName(productDto.getName());
        entity.setDescription(productDto.getDescription());
        entity.setPrice(productDto.getPrice());

        ProductEntity saved = productRepository.save(entity);

        return mapToDto(saved);
    }

    public ProductDto get(Long id) {

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return mapToDto(entity);
    }

    private ProductDto mapToDto(ProductEntity entity) {
        return new ProductDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getRating()
        );
    }
}
