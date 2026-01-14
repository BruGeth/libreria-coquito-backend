package com.coquito.backend.repository;

import com.coquito.backend.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    @Query("SELECT sm FROM StockMovement sm WHERE sm.product.id = :productId ORDER BY sm.movementDate DESC")
    List<StockMovement> findByProductId(@Param("productId") Long productId);

    @Query("SELECT sm FROM StockMovement sm WHERE sm.type = :type ORDER BY sm.movementDate DESC")
    List<StockMovement> findByType(@Param("type") StockMovement.MovementType type);

    @Query("SELECT sm FROM StockMovement sm WHERE sm.movementDate BETWEEN :from AND :to ORDER BY sm.movementDate DESC")
    List<StockMovement> findByMovementDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
