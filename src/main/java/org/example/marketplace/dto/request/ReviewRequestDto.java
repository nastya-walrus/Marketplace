package org.example.marketplace.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private Long buyerId;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotNull(message = "Поле не должно быть пустым")
    private String comment;
}
