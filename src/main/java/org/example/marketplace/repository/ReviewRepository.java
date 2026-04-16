package org.example.marketplace.repository;

import org.example.marketplace.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.productId = :productId")
    Double calculateAverageRating(Long productId);

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);
}
