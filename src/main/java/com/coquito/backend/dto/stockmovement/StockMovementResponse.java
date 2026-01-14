package com.coquito.backend.dto.stockmovement;

import com.coquito.backend.entity.StockMovement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponse {

    private Long id;
    private Long productId;
    private String productName;
    private StockMovement.MovementType type;
    private Integer quantity;
    private Integer previousStock;
    private Integer newStock;
    private LocalDateTime movementDate;
    private String reason;
    private Long referenceId;
    private String referenceType;
}
