package com.coquito.backend.service;

import com.coquito.backend.dto.stockmovement.StockMovementRequest;
import com.coquito.backend.dto.stockmovement.StockMovementResponse;
import com.coquito.backend.entity.Product;
import com.coquito.backend.entity.StockMovement;

import java.time.LocalDateTime;
import java.util.List;

public interface StockService {

    StockMovementResponse registerMovement(StockMovementRequest request);

    StockMovementResponse registerMovement(Product product, StockMovement.MovementType type, 
                                           Integer quantity, String reason, Long referenceId, 
                                           String referenceType);

    List<StockMovementResponse> findAll();

    List<StockMovementResponse> findByProductId(Long productId);

    List<StockMovementResponse> findByDateRange(LocalDateTime from, LocalDateTime to);

    void validateStockAvailability(Long productId, Integer quantity);
}
