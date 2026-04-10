package org.example.marketplace.service;

import org.example.marketplace.dto.request.ReviewRequestDto;
import org.example.marketplace.repository.PurchaseRepository;
import org.example.marketplace.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void review_shouldThrow_ifProductNotPurchased() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);

        when(purchaseRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(false);

        assertThrows(IllegalStateException.class,
                () -> reviewService.review(10L, dto));

        verify(purchaseRepository).existsByBuyerIdAndProductId(1L, 10L);
        verifyNoInteractions(reviewRepository);
    }

    @Test
    void review_shouldThrow_ifAlreadyReviewed() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);

        when(purchaseRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(true);
        when(reviewRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> reviewService.review(10L, dto));

        verify(purchaseRepository).existsByBuyerIdAndProductId(1L, 10L);
        verify(reviewRepository).existsByBuyerIdAndProductId(1L, 10L);
    }

    @Test
    void review_shouldPassValidation() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);

        when(purchaseRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(true);
        when(reviewRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(false);

        assertDoesNotThrow(() -> reviewService.review(10L, dto));

        verify(purchaseRepository).existsByBuyerIdAndProductId(1L, 10L);
        verify(reviewRepository).existsByBuyerIdAndProductId(1L, 10L);
    }
}