package org.example.marketplace.mapper;

import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductDto toDto(ProductEntity entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, ProductDto.class);
    }

    public ProductEntity toEntity(ProductDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, ProductEntity.class);
    }
}
