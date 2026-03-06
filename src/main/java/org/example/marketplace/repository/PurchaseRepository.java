package org.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository {

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);
}
