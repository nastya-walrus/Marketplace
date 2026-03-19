package org.example.marketplace.mapper;

import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.springframework.stereotype.Component;

@Component
public class BuyerMapper {

    public BuyerDto toDto(BuyerEntity entity) {
        BuyerDto buyerDto = new BuyerDto();
        buyerDto.setId(entity.getId());
        buyerDto.setName(entity.getName());
        buyerDto.setEmail(entity.getEmail());
        buyerDto.setBalance(entity.getBalance());
        buyerDto.setCreatedAt(entity.getCreatedAt());
        return buyerDto;
    }

    public BuyerEntity toEntity(BuyerDto buyerDto) {
        BuyerEntity buyerEntity = new BuyerEntity();
        buyerEntity.setId(buyerDto.getId());
        buyerEntity.setName(buyerDto.getName());
        buyerEntity.setEmail(buyerDto.getEmail());
        buyerEntity.setBalance(buyerDto.getBalance());
        buyerEntity.setCreatedAt(buyerDto.getCreatedAt());
        return buyerEntity;
    }
}
