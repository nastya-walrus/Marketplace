package org.example.marketplace.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.request.ReviewRequestDto;
import org.example.marketplace.repository.PurchaseRepository;
import org.example.marketplace.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PurchaseRepository purchaseRepository;

    @Transactional
    public void review(Long productId, ReviewRequestDto dto) {
        validateBuyerBoughtProduct(productId, dto);
        validateBuyerHaveNotReviewedThisProductBefore(productId, dto);
    }

    private void validateBuyerHaveNotReviewedThisProductBefore(Long productId, ReviewRequestDto dto) {
        boolean reviewExists = reviewRepository.existsByBuyerIdAndProductId(
                dto.getBuyerId(), productId);
        if (reviewExists) {
            throw new IllegalStateException("This product is already reviewed");
        }
    }

    private void validateBuyerBoughtProduct(Long productId, ReviewRequestDto dto) {
        boolean purchased = purchaseRepository
                .existsByBuyerIdAndProductId(dto.getBuyerId(), productId);
        if (!purchased) {
            throw new IllegalStateException("This product is not purchased");
        }
    }
}
