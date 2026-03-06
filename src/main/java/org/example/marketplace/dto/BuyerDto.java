package org.example.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDto {

    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;
    private Timestamp createdAt;
}
