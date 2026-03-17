package org.example.marketplace.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.repository.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyerService {

    private final BuyerRepository buyerRepository;

    @Transactional
    public BuyerDto create(BuyerDto buyerDto) {

        if (buyerRepository.existsByEmail(buyerDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        BuyerEntity entity = new BuyerEntity();
        entity.setName(buyerDto.getName());
        entity.setEmail(buyerDto.getEmail());
        entity.setBalance(buyerDto.getBalance());

        BuyerEntity saved = buyerRepository.save(entity);

        return mapToDto(saved);
    }

    public BuyerDto get(Long id) {
        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
        return mapToDto(entity);
    }

    @Transactional
    public BuyerDto deposit(Long id, @DecimalMin("0.01") BigDecimal amount) {

        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Buyer not found"));

        entity.setBalance(entity.getBalance().add(amount));

        return mapToDto(entity);
    }

    private BuyerDto mapToDto(BuyerEntity entity) {
        return new BuyerDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getBalance(),
                entity.getCreatedAt()
        );
    }
}
