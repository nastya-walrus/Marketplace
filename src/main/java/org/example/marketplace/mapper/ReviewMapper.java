package org.example.marketplace.mapper;

import org.example.marketplace.dto.ReviewDto;
import org.example.marketplace.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toDto(ReviewEntity entity);

    ReviewEntity toEntity(ReviewDto dto);
}
