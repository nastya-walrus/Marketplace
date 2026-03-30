package org.example.marketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class BuyResponseDto {

    private Long purchaseId;
    private BigDecimal remainingBalance;
}
