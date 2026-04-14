package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.mapper.ProductMapper;
import org.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto create(ProductDto productDto) {
        log.info("Creating product: name={}", productDto.getName());

        ProductEntity entity = productMapper.toEntity(productDto);
        ProductEntity saved = productRepository.save(entity);

        log.info("Product created successfully with id={}", saved.getId());
        log.debug("Created product details: {}", saved);

        return productMapper.toDto(saved);
    }

    public ProductDto get(Long id) {
        log.info("Fetching product by id={}", id);

        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product not found with id={}", id);
                    return new EntityNotFoundException("Product not found");
                });

        log.debug("Product found: id={}, name={}", entity.getId(), entity.getName());
        return productMapper.toDto(entity);
    }
}
