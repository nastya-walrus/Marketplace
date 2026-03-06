package org.example.marketplace.mapper.response;

import org.example.marketplace.dto.response.BuyerResponseDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.entity.PurchaseEntity;
import org.example.marketplace.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BuyerResponseMapper {

    @Mapping(target = "purchaseIds", source = "purchases")
    @Mapping(target = "reviewIds", source = "reviews")
    BuyerResponseDto toDto(BuyerEntity entity);

    default List<Long> map(List<PurchaseEntity> purchases) {
        if (purchases == null) return null;
        return purchases.stream()
                .map(PurchaseEntity::getId)
                .collect(Collectors.toList());
    }

    default List<Long> mapReviews(List<ReviewEntity> reviews) {
        if (reviews == null) return null;
        return reviews.stream()
                .map(ReviewEntity::getId)
                .collect(Collectors.toList());
    }
}
