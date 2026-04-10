package org.example.marketplace.mapper;

import org.example.marketplace.dto.ReviewDto;
import org.example.marketplace.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewMapperTest {

    private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void toDto() {
        ReviewEntity entity = new ReviewEntity(
                1L,
                2L,
                3L,
                5,
                "Great product",
                new Timestamp(341234124L)
        );

        ReviewDto dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getBuyerId());
        assertEquals(3L, dto.getProductId());
        assertEquals(5, dto.getRating());
        assertEquals("Great product", dto.getComment());
        assertEquals(new Timestamp(341234124L), dto.getCreatedAt());
    }

    @Test
    void toEntity() {
        ReviewDto dto = new ReviewDto(
                1L,
                2L,
                3L,
                5,
                "Great product",
                new Timestamp(341234124L)
        );

        ReviewEntity entity = mapper.toEntity(dto);

        assertEquals(1L, entity.getId());
        assertEquals(2L, entity.getBuyerId());
        assertEquals(3L, entity.getProductId());
        assertEquals(5, entity.getRating());
        assertEquals("Great product", entity.getComment());
        assertEquals(new Timestamp(341234124L), entity.getCreatedAt());
    }
}