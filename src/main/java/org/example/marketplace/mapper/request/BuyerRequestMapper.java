package org.example.marketplace.mapper.request;

import org.example.marketplace.dto.request.BuyerRequestDto;
import org.example.marketplace.entity.BuyerEntity;;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BuyerRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    BuyerEntity toEntity(BuyerRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateFromDto(BuyerRequestDto dto,
                       @MappingTarget BuyerEntity entity);
}
