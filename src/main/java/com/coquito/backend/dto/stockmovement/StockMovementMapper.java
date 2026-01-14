package com.coquito.backend.dto.stockmovement;

import com.coquito.backend.entity.StockMovement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMovementMapper {

    public StockMovementResponse toResponse(StockMovement movement) {
        if (movement == null) {
            return null;
        }

        return StockMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProduct().getId())
                .productName(movement.getProduct().getName())
                .type(movement.getType())
                .quantity(movement.getQuantity())
                .previousStock(movement.getPreviousStock())
                .newStock(movement.getNewStock())
                .movementDate(movement.getMovementDate())
                .reason(movement.getReason())
                .referenceId(movement.getReferenceId())
                .referenceType(movement.getReferenceType())
                .build();
    }

    public List<StockMovementResponse> toResponseList(List<StockMovement> movements) {
        if (movements == null) {
            return null;
        }
        return movements.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
