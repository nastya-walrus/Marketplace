package org.example.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.ReviewDto;
import org.example.marketplace.dto.request.ReviewRequestDto;
import org.example.marketplace.service.ReviewService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/review/products/{productId}")
    public ReviewDto review(@PathVariable Long productId,
                            @RequestBody ReviewRequestDto reviewRequestDto) {

        return reviewService.review(productId, reviewRequestDto);
    }
}
