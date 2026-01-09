package com.coquito.backend.service;

import com.coquito.backend.entity.InventoryMovement;
import java.util.List;

public interface InventoryMovementService {

    InventoryMovement register(InventoryMovement movement);

    InventoryMovement findById(Long id);

    List<InventoryMovement> findAll();
}
