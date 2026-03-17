package org.example.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.example.marketplace.dto.BuyerDto;
import org.example.marketplace.dto.request.DepositRequestDto;
import org.example.marketplace.dto.response.DepositResponseDto;
import org.example.marketplace.service.BuyerService;
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
    public BuyerDto createBuyer(@RequestBody BuyerDto buyerDto) {
        return buyerService.create(buyerDto);
    }

    @GetMapping("/buyers/{id}")
    public BuyerDto getBuyer(@PathVariable Long id) {
        return buyerService.get(id);
    }

    @PostMapping("/buyers/{id}/deposit")
    public DepositResponseDto deposit(@PathVariable Long id,
                                      @RequestBody DepositRequestDto depositRequestDto) {
        BuyerDto buyerDto = buyerService.deposit(id, depositRequestDto.getAmount());
        return new DepositResponseDto(buyerDto.getId(), buyerDto.getBalance());
    }
}
