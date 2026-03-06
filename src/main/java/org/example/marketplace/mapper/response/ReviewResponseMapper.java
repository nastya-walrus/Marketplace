package org.example.marketplace.mapper.response;

import org.example.marketplace.dto.response.ReviewResponseDto;
import org.example.marketplace.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper {

    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "productId", source = "product.id")
    ReviewResponseDto toDto(ReviewEntity entity);
}
