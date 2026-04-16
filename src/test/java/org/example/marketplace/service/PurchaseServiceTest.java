package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.marketplace.dto.response.BuyResponseDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.entity.PurchaseEntity;
import org.example.marketplace.exception.InsufficientFundsException;
import org.example.marketplace.repository.BuyerRepository;
import org.example.marketplace.repository.ProductRepository;
import org.example.marketplace.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void buy_shouldFail_ifProductNotFound() {
        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> purchaseService.buy(10L, 1L));

        verify(productRepository).findById(10L);
        verifyNoInteractions(buyerRepository, purchaseRepository);
    }

    @Test
    void buy_shouldFail_ifBuyerNotFound_afterWriteOff() {
        BigDecimal price = new BigDecimal("100.00");

        ProductEntity product = new ProductEntity();
        product.setPrice(price);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.writeOffFunds(1L, price)).thenReturn(0);
        when(buyerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> purchaseService.buy(10L, 1L));

        verify(productRepository).findById(10L);
        verify(buyerRepository).writeOffFunds(1L, price);
        verify(buyerRepository).findById(1L);
        verifyNoInteractions(purchaseRepository);
    }

    @Test
    void buy_shouldFail_ifNotEnoughMoney() {
        BigDecimal price = new BigDecimal("100.00");

        ProductEntity product = new ProductEntity();
        product.setPrice(price);

        BuyerEntity buyer = new BuyerEntity();
        buyer.setBalance(new BigDecimal("50.00"));

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.writeOffFunds(1L, price)).thenReturn(0);
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

        assertThrows(InsufficientFundsException.class,
                () -> purchaseService.buy(10L, 1L));

        verify(productRepository).findById(10L);
        verify(buyerRepository).writeOffFunds(1L, price);
        verify(buyerRepository).findById(1L);
        verifyNoInteractions(purchaseRepository);
    }

    @Test
    void buy_shouldBuy() {
        BigDecimal price = new BigDecimal("100.00");

        ProductEntity product = new ProductEntity();
        product.setPrice(price);

        PurchaseEntity savedPurchase = new PurchaseEntity();
        savedPurchase.setId(5L);

        BuyerEntity buyer = new BuyerEntity();
        buyer.setBalance(new BigDecimal("900.00"));

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.writeOffFunds(1L, price)).thenReturn(1);
        when(purchaseRepository.save(any(PurchaseEntity.class)))
                .thenReturn(savedPurchase);
        when(buyerRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        BuyResponseDto result = purchaseService.buy(10L, 1L);

        assertNotNull(result);
        assertEquals(5L, result.getPurchaseId());
        assertEquals(0, result.getRemainingBalance().compareTo(buyer.getBalance()));

        verify(productRepository).findById(10L);
        verify(buyerRepository).writeOffFunds(1L, price);
        verify(purchaseRepository).save(any(PurchaseEntity.class));
        verify(buyerRepository).findById(1L);
    }
}