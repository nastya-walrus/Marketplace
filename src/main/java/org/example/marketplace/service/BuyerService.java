package org.example.marketplace.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.exception.EntityConflictException;
import org.example.marketplace.mapper.BuyerMapper;
import org.example.marketplace.repository.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final BuyerMapper buyerMapper;
    EntityConflictException entityConflictException;

    @Transactional
    public BuyerDto create(BuyerDto buyerDto) {

        if (buyerRepository.existsByEmail(buyerDto.getEmail())) {
            throw new EntityConflictException("Email already exists: " + buyerDto.getEmail());
        }

        BuyerEntity entity = buyerMapper.toEntity(buyerDto);
        BuyerEntity saved = buyerRepository.save(entity);

        return buyerMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public BuyerDto get(Long id) {
        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        return buyerMapper.toDto(entity);
    }

    @Transactional
    public BuyerDto deposit(Long id, @DecimalMin("0.01") BigDecimal amount) {

        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));

        entity.setBalance(entity.getBalance().add(amount));

        return buyerMapper.toDto(buyerRepository.save(entity));
    }
}
