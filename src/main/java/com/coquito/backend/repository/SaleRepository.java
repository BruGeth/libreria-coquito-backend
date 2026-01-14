package com.coquito.backend.repository;

import com.coquito.backend.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :from AND :to ORDER BY s.saleDate DESC")
    List<Sale> findBySaleDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT s FROM Sale s WHERE s.status = :status ORDER BY s.saleDate DESC")
    List<Sale> findByStatus(@Param("status") Sale.SaleStatus status);

    @Query("SELECT s FROM Sale s ORDER BY s.saleDate DESC")
    List<Sale> findAllOrderByDateDesc();
}
