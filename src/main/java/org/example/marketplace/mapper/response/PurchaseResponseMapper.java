package org.example.marketplace.mapper.response;

import org.example.marketplace.dto.response.PurchaseResponseDto;
import org.example.marketplace.entity.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface PurchaseResponseMapper {

        @Mapping(target = "buyerId", source = "buyer.id")
        @Mapping(target = "productId", source = "product.id")
        PurchaseResponseDto toDto(PurchaseEntity entity);
}
