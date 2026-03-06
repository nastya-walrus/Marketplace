package org.example.marketplace.mapper.request;

import org.example.marketplace.dto.request.ReviewRequestDto;
import org.example.marketplace.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "product", ignore = true)
    ReviewEntity toEntity(ReviewRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateFromDto(ReviewRequestDto dto,
                       @MappingTarget ReviewEntity entity);
}
