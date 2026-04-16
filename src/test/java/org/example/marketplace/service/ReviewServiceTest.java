package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.marketplace.dto.ReviewDto;
import org.example.marketplace.dto.request.ReviewRequestDto;
import org.example.marketplace.entity.BuyerEntity;
import org.example.marketplace.entity.ProductEntity;
import org.example.marketplace.entity.ReviewEntity;
import org.example.marketplace.mapper.ReviewMapper;
import org.example.marketplace.repository.BuyerRepository;
import org.example.marketplace.repository.ProductRepository;
import org.example.marketplace.repository.PurchaseRepository;
import org.example.marketplace.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BuyerRepository buyerRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void review_shouldThrow_ifProductNotFound() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);
        dto.setRating(5);

        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> reviewService.review(10L, dto));

        verify(productRepository).findById(10L);
        verifyNoInteractions(purchaseRepository, reviewRepository, buyerRepository);
    }

    @Test
    void review_shouldThrow_ifBuyerNotFound() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);
        dto.setRating(5);

        ProductEntity product = new ProductEntity();
        product.setId(10L);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> reviewService.review(10L, dto));

        verify(productRepository).findById(10L);
        verify(buyerRepository).findById(1L);
        verifyNoInteractions(purchaseRepository, reviewRepository);
    }

    @Test
    void review_shouldThrow_ifProductNotPurchased() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);
        dto.setRating(5);

        ProductEntity product = new ProductEntity();
        product.setId(10L);

        BuyerEntity buyer = new BuyerEntity();
        buyer.setId(1L);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
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
        dto.setRating(5);

        ProductEntity product = new ProductEntity();
        product.setId(10L);

        BuyerEntity buyer = new BuyerEntity();
        buyer.setId(1L);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

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
    void review_shouldPassValidation_andCreateReview() {
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setBuyerId(1L);
        dto.setRating(5);
        dto.setComment("Good");

        ProductEntity product = new ProductEntity();
        product.setId(10L);

        BuyerEntity buyer = new BuyerEntity();
        buyer.setId(1L);

        ReviewEntity saved = new ReviewEntity();
        saved.setId(100L);
        saved.setBuyerId(1L);
        saved.setProductId(10L);

        ReviewDto mappedDto = new ReviewDto();
        mappedDto.setId(100L);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));

        when(purchaseRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(true);
        when(reviewRepository.existsByBuyerIdAndProductId(1L, 10L))
                .thenReturn(false);

        when(reviewRepository.save(any(ReviewEntity.class)))
                .thenReturn(saved);

        when(reviewMapper.toDto(saved)).thenReturn(mappedDto);

        ReviewDto result = reviewService.review(10L, dto);

        assertNotNull(result);
        assertEquals(100L, result.getId());

        verify(productRepository).findById(10L);
        verify(buyerRepository).findById(1L);
        verify(purchaseRepository).existsByBuyerIdAndProductId(1L, 10L);
        verify(reviewRepository).existsByBuyerIdAndProductId(1L, 10L);
        verify(reviewRepository).save(any(ReviewEntity.class));
        verify(reviewMapper).toDto(saved);
    }
}