package com.coquito.backend.dto.inventorymovement;

import com.coquito.backend.dto.product.ProductMapper;
import com.coquito.backend.entity.InventoryMovement;
import com.coquito.backend.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMovementMapper {

    private final ProductMapper productMapper;

    public InventoryMovement toEntity(InventoryMovementRequest request, Product product) {
        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setQuantity(request.getQuantity());
        movement.setType(request.getMovementType());
        movement.setReason(request.getReason());
        return movement;
    }

    public InventoryMovementResponse toResponse(InventoryMovement movement) {
        return InventoryMovementResponse.builder()
                .id(movement.getId())
                .product(productMapper.toResponse(movement.getProduct()))
                .movementType(movement.getType())
                .quantity(movement.getQuantity())
                .reason(movement.getReason())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}
