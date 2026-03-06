package org.example.marketplace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDto {

    private Long id;
    private Long buyerId;
    private Long productId;
    private Timestamp purchaseDate;
    private BigDecimal priceAtPurchase;
}
