package org.example.marketplace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long buyerId;
    private Long productId;

    @Column(name = "purchase_date")
    @CreatedDate
    private Timestamp purchaseDate;

    @Column(name = "price_at_purchase", precision = 19, scale = 2)
    private BigDecimal priceAtPurchase;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(buyerId, that.buyerId) && Objects.equals(purchaseDate, that.purchaseDate) && Objects.equals(priceAtPurchase, that.priceAtPurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyerId, purchaseDate, priceAtPurchase);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PurchaseEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("buyerId=" + buyerId)
                .add("purchaseDate=" + purchaseDate)
                .add("priceAtPurchase=" + priceAtPurchase)
                .toString();
    }
}
