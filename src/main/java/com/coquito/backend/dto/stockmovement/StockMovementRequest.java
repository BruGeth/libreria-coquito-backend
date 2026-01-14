package com.coquito.backend.dto.stockmovement;

import com.coquito.backend.entity.StockMovement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Movement type is required")
    private StockMovement.MovementType type;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Reason is required")
    @Size(min = 3, max = 500, message = "Reason must be between 3 and 500 characters")
    private String reason;
}
