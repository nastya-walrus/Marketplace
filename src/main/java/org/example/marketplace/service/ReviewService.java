package org.example.marketplace.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final BuyerRepository buyerRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewDto review(Long productId, ReviewRequestDto dto) {
        log.info("Start review: buyerId={}, productId={}, rating={}",
                dto.getBuyerId(), productId, dto.getRating());

        validateBuyerBoughtProduct(productId, dto);
        validateBuyerHaveNotReviewedThisProductBefore(productId, dto);

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.warn("Review failed: product not found, productId={}", productId);
                    return new EntityNotFoundException("Product not found");
                });

        BuyerEntity buyer = buyerRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> {
                    log.warn("Review failed: buyer not found, buyerId={}", dto.getBuyerId());
                    return new EntityNotFoundException("User not found");
                });

        ReviewEntity review = new ReviewEntity();
        review.setBuyerId(buyer.getId());
        review.setProductId(product.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        ReviewEntity saved = reviewRepository.save(review);

        log.info("Review created: reviewId={}, buyerId={}, productId={}",
                saved.getId(), buyer.getId(), product.getId());

        log.debug("Review details: {}", saved);

        return reviewMapper.toDto(saved);
    }

    private void validateBuyerHaveNotReviewedThisProductBefore(Long productId, ReviewRequestDto dto) {
        boolean reviewExists = reviewRepository.existsByBuyerIdAndProductId(
                dto.getBuyerId(), productId);

        if (reviewExists) {
            log.warn("Validation failed: review already exists, buyerId={}, productId={}",
                    dto.getBuyerId(), productId);
            throw new IllegalStateException("This product is already reviewed");
        }

        log.debug("Validation passed: no existing review, buyerId={}, productId={}",
                dto.getBuyerId(), productId);
    }

    private void validateBuyerBoughtProduct(Long productId, ReviewRequestDto dto) {
        boolean purchased = purchaseRepository
                .existsByBuyerIdAndProductId(dto.getBuyerId(), productId);

        if (!purchased) {
            log.warn("Validation failed: product not purchased, buyerId={}, productId={}",
                    dto.getBuyerId(), productId);
            throw new IllegalStateException("This product is not purchased");
        }

        log.debug("Validation passed: product purchased, buyerId={}, productId={}",
                dto.getBuyerId(), productId);
    }
}
