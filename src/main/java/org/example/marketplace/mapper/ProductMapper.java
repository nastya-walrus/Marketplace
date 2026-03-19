package org.example.marketplace.mapper;

import org.example.marketplace.dto.ProductDto;
import org.example.marketplace.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(ProductEntity entity) {
        ProductDto productDto = new ProductDto();
        productDto.getId();
        productDto.setName(entity.getName());
        productDto.setDescription(entity.getDescription());
        productDto.setPrice(entity.getPrice());
        productDto.setRating(entity.getRating());
        return productDto;
    }

    public ProductEntity toEntity(ProductDto dto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(dto.getId());
        productEntity.setName(dto.getName());
        productEntity.setDescription(dto.getDescription());
        productEntity.setPrice(dto.getPrice());
        productEntity.setRating(dto.getRating());
        productEntity.setRating(dto.getRating());
        return productEntity;
    }
}
