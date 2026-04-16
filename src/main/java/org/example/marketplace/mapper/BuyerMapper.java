package org.example.marketplace.mapper;

import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface BuyerMapper {

    BuyerDto toDto(BuyerEntity entity);
    BuyerEntity toEntity(BuyerDto dto);
}
