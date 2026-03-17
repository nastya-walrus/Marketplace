package org.example.marketplace.repository;

import org.example.marketplace.entity.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity,Long> {

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);
}
