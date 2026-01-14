package com.coquito.backend.dto.inventorymovement;

import com.coquito.backend.dto.product.ProductResponse;
import com.coquito.backend.entity.MovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovementResponse {
    
    private Long id;
    private ProductResponse product;
    private MovementType movementType;
    private Integer quantity;
    private String reason;
    private LocalDateTime createdAt;
}
