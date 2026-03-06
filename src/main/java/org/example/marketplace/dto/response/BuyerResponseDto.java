package org.example.marketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerResponseDto {

    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;
    private List<Long> purchaseIds;
    private List<Long> reviewIds;
}
