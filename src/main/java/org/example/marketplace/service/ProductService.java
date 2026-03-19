package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.mapper.ProductMapper;
import org.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto create(ProductDto productDto) {

        ProductEntity entity = productMapper.toEntity(productDto);
        ProductEntity saved = productRepository.save(entity);

        return productMapper.toDto(saved);
    }

    public ProductDto get(Long id) {

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return productMapper.toDto(entity);
    }
}
