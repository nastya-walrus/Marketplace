package org.example.marketplace.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDto {

    @DecimalMin("0.01")
    private BigDecimal amount;
}
