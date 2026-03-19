package org.example.marketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long buyerId;
    private Long productId;

    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000)
    private String comment;

    @Column(name = "created_at")
    @CreatedDate
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(buyerId, that.buyerId) && Objects.equals(productId, that.productId) && Objects.equals(rating, that.rating) && Objects.equals(comment, that.comment) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyerId, productId, rating, comment, createdAt);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ReviewEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("buyerId=" + buyerId)
                .add("productId=" + productId)
                .add("rating=" + rating)
                .add("comment='" + comment + "'")
                .add("createdAt=" + createdAt)
                .toString();
    }
}
