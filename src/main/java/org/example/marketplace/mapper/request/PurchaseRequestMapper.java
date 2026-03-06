package org.example.marketplace.mapper.request;

import org.example.marketplace.dto.request.PurchaseRequestDto;
import org.example.marketplace.entity.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PurchaseRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "priceAtPurchase", ignore = true)
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "product", ignore = true)
    PurchaseEntity toEntity(PurchaseRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "priceAtPurchase", ignore = true)
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateFromDto(PurchaseRequestDto dto,
                       @MappingTarget PurchaseEntity entity);
}
