package org.example.marketplace.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerRequestDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @DecimalMin("0.00")
    private BigDecimal balance;

}
