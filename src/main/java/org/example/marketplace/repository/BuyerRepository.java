package org.example.marketplace.repository;

import org.example.marketplace.entity.BuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface BuyerRepository extends JpaRepository<BuyerEntity, Long>  {

    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE BuyerEntity b SET b.balance = b.balance - :price WHERE b.id = :id AND b.balance >= :price")
    int writeOffFunds(@Param("id") Long id, @Param("price") BigDecimal price);
}
