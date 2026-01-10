package com.coquito.backend.controller;

import com.coquito.backend.entity.InventoryMovement;
import com.coquito.backend.service.InventoryMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-movements")
@RequiredArgsConstructor
public class InventoryMovementController {

    private final InventoryMovementService inventoryMovementService;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryMovement register(@RequestBody InventoryMovement movement) {
        return inventoryMovementService.register(movement);
    }

    @GetMapping(version = "1")
    public List<InventoryMovement> findAll() {
        return inventoryMovementService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public InventoryMovement findById(@PathVariable Long id) {
        return inventoryMovementService.findById(id);
    }
}
