package com.coquito.backend.controller;

import com.coquito.backend.dto.inventorymovement.InventoryMovementRequest;
import com.coquito.backend.dto.inventorymovement.InventoryMovementResponse;
import com.coquito.backend.service.InventoryMovementService;
import jakarta.validation.Valid;
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
    public InventoryMovementResponse register(@Valid @RequestBody InventoryMovementRequest request) {
        return inventoryMovementService.register(request);
    }

    @GetMapping(version = "1")
    public List<InventoryMovementResponse> findAll() {
        return inventoryMovementService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public InventoryMovementResponse findById(@PathVariable Long id) {
        return inventoryMovementService.findById(id);
    }
}
