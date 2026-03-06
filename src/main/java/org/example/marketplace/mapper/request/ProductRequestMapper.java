package org.example.marketplace.mapper.request;

import org.example.marketplace.dto.request.ProductRequestDto;
import org.example.marketplace.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    ProductEntity toEntity(ProductRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateFromDto(ProductRequestDto dto,
                       @MappingTarget ProductEntity entity);
}
