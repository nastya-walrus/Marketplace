package org.example.marketplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.dto.request.DepositRequestDto;
import org.example.marketplace.dto.response.DepositResponseDto;
import org.example.marketplace.service.BuyerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping("/buyers")
    public ResponseEntity<BuyerDto> createBuyer(@RequestBody @Valid BuyerDto buyerDto) {
        BuyerDto created = buyerService.create(buyerDto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/buyers/{id}")
    public ResponseEntity<BuyerDto> getBuyer(@PathVariable Long id) {
        BuyerDto buyer = buyerService.get(id);
        return ResponseEntity.ok(buyer);
    }

    @PostMapping("/buyers/{id}/deposit")
    public ResponseEntity<DepositResponseDto> deposit(@PathVariable Long id,
                                                      @RequestBody DepositRequestDto depositRequestDto) {
        BuyerDto buyerDto = buyerService.deposit(id, depositRequestDto.getAmount());
        DepositResponseDto response =
                new DepositResponseDto(buyerDto.getId(), buyerDto.getBalance());

        return ResponseEntity.ok(response);
    }
}
