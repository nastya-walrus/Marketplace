package org.example.marketplace.service;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.exception.EntityConflictException;
import org.example.marketplace.mapper.BuyerMapper;
import org.example.marketplace.repository.BuyerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final BuyerMapper buyerMapper;

    @Transactional
    public BuyerDto create(BuyerDto buyerDto) {
        log.info("Creating buyer with email={}", buyerDto.getEmail());

        if (buyerRepository.existsByEmail(buyerDto.getEmail())) {
            log.warn("Buyer creation failed: email already exists={}", buyerDto.getEmail());
            throw new EntityConflictException("Email already exists: " + buyerDto.getEmail());
        }

        buyerDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        BuyerEntity entity = buyerMapper.toEntity(buyerDto);
        BuyerEntity saved = buyerRepository.save(entity);
        log.info("Buyer created successfully with id={}", saved.getId());

        return buyerMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public BuyerDto get(Long id) {
        log.info("Fetching buyer by id={}", id);

        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Buyer not found with id={}", id);
                    return new EntityNotFoundException("Buyer not found");
                });

        log.debug("Buyer found: id={}, email={}", entity.getId(), entity.getEmail());
        return buyerMapper.toDto(entity);
    }

    @Transactional
    public BuyerDto deposit(Long id, @DecimalMin("0.01") BigDecimal amount) {
        log.info("Depositing amount={} to buyer id={}", amount, id);

        BuyerEntity entity = buyerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Deposit failed: buyer not found id={}", id);
                    return new EntityNotFoundException("Buyer not found");
                });

        BigDecimal oldBalance = entity.getBalance();
        entity.setBalance(oldBalance.add(amount));

        BuyerEntity saved = buyerRepository.save(entity);

        log.info("Deposit successful: id={}, oldBalance={}, newBalance={}",
                id, oldBalance, saved.getBalance());

        return buyerMapper.toDto(saved);
    }
}
