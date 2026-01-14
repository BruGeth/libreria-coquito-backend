package com.coquito.backend.service;

import com.coquito.backend.dto.inventorymovement.InventoryMovementRequest;
import com.coquito.backend.dto.inventorymovement.InventoryMovementResponse;
import java.util.List;

public interface InventoryMovementService {

    InventoryMovementResponse register(InventoryMovementRequest request);

    InventoryMovementResponse findById(Long id);

    List<InventoryMovementResponse> findAll();
}
