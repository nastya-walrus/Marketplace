package org.example.marketplace.mapper;

import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    void toDto() {
        Timestamp now = Timestamp.from(Instant.now());

        ProductEntity entity = new ProductEntity(
                1L,
                "iPhone",
                "Smartphone",
                new BigDecimal("999.99"),
                new BigDecimal("4.50"),
                now
        );

        ProductDto dto = modelMapper.map(entity, ProductDto.class);

        assertEquals(1L, dto.getId());
        assertEquals("iPhone", dto.getName());
        assertEquals("Smartphone", dto.getDescription());
        assertEquals(0, dto.getPrice().compareTo(new BigDecimal("999.99")));
        assertEquals(0, dto.getRating().compareTo(new BigDecimal("4.50")));

        // createdAt отсутствует в DTO → просто убеждаемся, что маппинг прошёл
        assertNotNull(dto);
    }

    @Test
    void toEntity() {
        ProductDto dto = new ProductDto(
                2L,
                "MacBook",
                "Laptop",
                new BigDecimal("1999.99"),
                new BigDecimal("4.80")
        );

        ProductEntity entity = modelMapper.map(dto, ProductEntity.class);

        assertEquals(2L, entity.getId());
        assertEquals("MacBook", entity.getName());
        assertEquals("Laptop", entity.getDescription());
        assertEquals(0, entity.getPrice().compareTo(new BigDecimal("1999.99")));
        assertEquals(0, entity.getRating().compareTo(new BigDecimal("4.80")));

        // createdAt не приходит из DTO → будет null
        assertNull(entity.getCreatedAt());
    }
}