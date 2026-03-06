package org.example.marketplace.mapper;

import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BuyerMapperTest {

    private final BuyerMapper mapper = Mappers.getMapper(BuyerMapper.class);

    @Test
    void toDto() {
        BuyerEntity entity = new BuyerEntity(
                1L,
                "John",
                "john@mail.ru",
                BigDecimal.valueOf(100),
                new Timestamp(341234124L));

        BuyerDto dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals("john@mail.ru", dto.getEmail());
        assertEquals(new BigDecimal("100"), dto.getBalance());
        assertEquals(new Timestamp(341234124L), dto.getCreatedAt());
    }

    @Test
    void toEntity() {
        BuyerDto dto = new BuyerDto(
                    1L,
                "John",
                "john@mail.ru",
                BigDecimal.valueOf(100),
                new Timestamp(341234124L));

        BuyerEntity entity = mapper.toEntity(dto);

        assertEquals(1L, entity.getId());
        assertEquals("John", entity.getName());
        assertEquals("john@mail.ru", entity.getEmail());
        assertEquals(new BigDecimal("100"), entity.getBalance());
        assertEquals(new Timestamp(341234124L), entity.getCreatedAt());
    }
}