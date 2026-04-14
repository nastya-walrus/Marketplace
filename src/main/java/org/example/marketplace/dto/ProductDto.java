package org.example.marketplace.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotNull(message = "Название товара не может быть пустым")
    private String name;

    private String description;

    @Min(1)
    private BigDecimal price;

    private BigDecimal rating;
}
