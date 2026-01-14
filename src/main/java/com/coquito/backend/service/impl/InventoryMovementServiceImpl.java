package com.coquito.backend.service.impl;

import com.coquito.backend.dto.inventorymovement.InventoryMovementMapper;
import com.coquito.backend.dto.inventorymovement.InventoryMovementRequest;
import com.coquito.backend.dto.inventorymovement.InventoryMovementResponse;
import com.coquito.backend.entity.InventoryMovement;
import com.coquito.backend.entity.Product;
import com.coquito.backend.repository.InventoryMovementRepository;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.service.InventoryMovementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductRepository productRepository;
    private final InventoryMovementMapper inventoryMovementMapper;

    @Override
    public InventoryMovementResponse register(InventoryMovementRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        int newStock = product.getStock() + request.getQuantity();
        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        product.setStock(newStock);
        productRepository.save(product);

        InventoryMovement movement = inventoryMovementMapper.toEntity(request, product);
        InventoryMovement saved = inventoryMovementRepository.save(movement);
        return inventoryMovementMapper.toResponse(saved);
    }

    @Override
    public InventoryMovementResponse findById(Long id) {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory movement not found"));
        return inventoryMovementMapper.toResponse(movement);
    }

    @Override
    public List<InventoryMovementResponse> findAll() {
        return inventoryMovementRepository.findAll().stream()
                .map(inventoryMovementMapper::toResponse)
                .collect(Collectors.toList());
    }
}
