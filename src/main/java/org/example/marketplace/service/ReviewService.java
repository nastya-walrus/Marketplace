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

        boolean purchased = purchaseRepository
                .existsByBuyerIdAndProductId(buyer.getId(), product.getId());

        if (!purchased) {
            log.warn("Review failed: product not purchased, buyerId={}, productId={}",
                    buyer.getId(), product.getId());
            throw new IllegalStateException("This product is not purchased");
        }

        boolean reviewExists = reviewRepository
                .existsByBuyerIdAndProductId(buyer.getId(), product.getId());

        if (reviewExists) {
            log.warn("Review failed: already reviewed, buyerId={}, productId={}",
                    buyer.getId(), product.getId());
            throw new IllegalStateException("This product is already reviewed");
        }

        ReviewEntity review = new ReviewEntity();
        review.setBuyerId(buyer.getId());
        review.setProductId(product.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        try {
            ReviewEntity saved = reviewRepository.save(review);

            log.info("Review created: reviewId={}, buyerId={}, productId={}",
                    saved.getId(), buyer.getId(), product.getId());

            return reviewMapper.toDto(saved);

        } catch (Exception ex) {
            log.warn("Review save failed, rechecking state. buyerId={}, productId={}",
                    buyer.getId(), product.getId());

            boolean existsNow = reviewRepository
                    .existsByBuyerIdAndProductId(buyer.getId(), product.getId());

            if (existsNow) {
                throw new IllegalStateException("This product is already reviewed");
            }

            throw ex;
        }
    }
}
