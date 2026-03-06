package org.example.marketplace.mapper.response;

import org.example.marketplace.dto.response.ProductResponseDto;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mapping(target = "reviewIds", source = "reviews")
    ProductResponseDto toDto(ProductEntity entity);

    default List<Long> map(List<ReviewEntity> reviews) {
        if (reviews == null) {
            return null;
        }

        return reviews.stream()
                .map(ReviewEntity::getId)
                .collect(Collectors.toList());
    }
}
