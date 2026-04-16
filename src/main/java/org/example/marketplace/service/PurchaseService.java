package org.example.marketplace.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.marketplace.dto.response.BuyResponseDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.entity.PurchaseEntity;
import org.example.marketplace.exception.InsufficientFundsException;
import org.example.marketplace.repository.BuyerRepository;
import org.example.marketplace.repository.ProductRepository;
import org.example.marketplace.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PurchaseService {

    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public BuyResponseDto buy(Long productId, Long buyerId) {
        log.info("Start purchase: buyerId={}, productId={}", buyerId, productId);

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Purchase failed: product not found, productId={}", productId);
                    return new EntityNotFoundException("Product not found " + productId);
                });

        BigDecimal price = product.getPrice();
        log.debug("Product fetched: productId={}, price={}", productId, price);

        int updatedRows = buyerRepository.writeOffFunds(buyerId, price);

        if (updatedRows == 0) {
            BuyerEntity buyer = buyerRepository.findById(buyerId)
                    .orElseThrow(() -> {
                        log.warn("Purchase failed: buyer not found, buyerId={}", buyerId);
                        return new EntityNotFoundException("Buyer not found " + buyerId);
                    });

            if (buyer.getBalance().compareTo(price) < 0) {
                log.warn("Purchase failed: insufficient funds, buyerId={}, balance={}, price={}",
                        buyerId, buyer.getBalance(), price);
                throw new InsufficientFundsException("Not enough money");
            }

            throw new IllegalStateException("Unexpected state during purchase");
        }

        log.info("Funds written off: buyerId={}, amount={}", buyerId, price);

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setBuyerId(buyerId);
        purchase.setProductId(productId);
        purchase.setPriceAtPurchase(price);

        PurchaseEntity saved = purchaseRepository.save(purchase);
        log.info("Purchase saved: purchaseId={}, buyerId={}, productId={}",
                saved.getId(), buyerId, productId);

        BigDecimal balance = buyerRepository.findById(buyerId)
                .map(BuyerEntity::getBalance)
                .orElse(BigDecimal.ZERO);

        log.info("Purchase completed: buyerId={}, purchaseId={}, remainingBalance={}",
                buyerId, saved.getId(), balance);

        return new BuyResponseDto(saved.getId(), balance);
    }
}
