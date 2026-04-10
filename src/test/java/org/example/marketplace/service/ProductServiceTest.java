package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.mapper.ProductMapper;
import org.example.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;


    @Test
    void createProduct() {
        ProductDto inputProductDto = mock();
        ProductDto outputProductDto = mock();
        ProductEntity beforeSave = mock();
        ProductEntity afterSave = mock();

        when(productMapper.toEntity(any())).thenReturn(beforeSave);
        when(productRepository.save(any())).thenReturn(afterSave);
        when(productMapper.toDto(any())).thenReturn(outputProductDto);

        ProductDto result = productService.create(inputProductDto);
        assertEquals(outputProductDto, result);
        verify(productMapper, times(1)).toEntity(inputProductDto);
        verify(productRepository, times(1)).save(beforeSave);
        verify(productMapper, times(1)).toDto(afterSave);
    }

    @Test
    void get_shouldThrowEntityNotFoundException() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> productService.get(1L)
        );
    }

    @Test
    void get_shouldReturnProduct() {
        long productId = 1L;

        ProductDto productDto = mock();
        ProductEntity productEntity = mock();
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));
        when(productMapper.toDto(any())).thenReturn(productDto);

        ProductDto result = productService.get(productId);
        assertEquals(productDto, result);
        verify(productRepository, times(1)).findById(productId);
        verify(productMapper, times(1)).toDto(productEntity);
    }
}