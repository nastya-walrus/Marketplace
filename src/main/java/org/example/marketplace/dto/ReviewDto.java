package org.example.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private Long buyerId;
    private Long productId;
    private Integer rating;
    private String comment;
    private Timestamp createdAt;
}
