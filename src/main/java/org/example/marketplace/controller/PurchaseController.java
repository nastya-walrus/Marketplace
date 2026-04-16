package org.example.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.response.BuyResponseDto;
import org.example.marketplace.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/purchase/products/{productId}/buy/{buyerId}")
    public ResponseEntity<BuyResponseDto> buy(@PathVariable Long productId,
                                              @PathVariable Long buyerId) {
        BuyResponseDto response = purchaseService.buy(productId, buyerId);
        return ResponseEntity.ok(response);
    }
}
