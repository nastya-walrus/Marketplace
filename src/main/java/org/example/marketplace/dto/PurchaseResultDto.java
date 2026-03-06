package org.example.marketplace.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseResultDto {

    private Long purchaseId;
    private BigDecimal remainingBalance;
}