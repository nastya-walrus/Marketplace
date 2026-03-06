package org.example.marketplace.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResponseDto {

    private Long buyerId;
    private BigDecimal newBalance;
}