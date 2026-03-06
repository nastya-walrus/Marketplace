package org.example.marketplace.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.response.BuyResponseDto;
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
public class PurchaseService {

    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public BuyResponseDto buy(Long productId, Long buyerId) {

        if(!buyerRepository.existsById(buyerId))
            throw new EntityNotFoundException("Buyer not found");

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        BigDecimal price = product.getPrice();

        int updatedRows = buyerRepository.writeOffFunds(buyerId, price);
        if (updatedRows == 0) {
            throw new InsufficientFundsException("Not enough money or buyer not found");
        }

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setBuyerId(productId);
        purchase.setProductId(buyerId);
        purchase.setPriceAtPurchase(price);

        PurchaseEntity saved = purchaseRepository.save(purchase);
        return new BuyResponseDto(
                saved.getId(),
                buyerRepository.findById(buyerId).get().getBalance()
        );
    }
}
