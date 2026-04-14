package org.example.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "Некорректный email")
    private String email;

    @Min(0)
    private BigDecimal balance;

    private Timestamp createdAt;
}
