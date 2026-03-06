package org.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository {

    @Query("SELECT AVG(r.rating) FROM ReviewEntity r WHERE r.product.id = :productId")
    Double calculateAverageRating(Long productId);
}
