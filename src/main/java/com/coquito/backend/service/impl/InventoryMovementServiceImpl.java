package com.coquito.backend.service.impl;

import com.coquito.backend.entity.InventoryMovement;
import com.coquito.backend.entity.Product;
import com.coquito.backend.repository.InventoryMovementRepository;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.service.InventoryMovementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryMovement register(InventoryMovement movement) {
        Product product = productRepository.findById(movement.getProduct().getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        int newStock = product.getStock() + movement.getQuantity();
        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        product.setStock(newStock);
        productRepository.save(product);

        return inventoryMovementRepository.save(movement);
    }

    @Override
    public InventoryMovement findById(Long id) {
        return inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventory movement not found"));
    }

    @Override
    public List<InventoryMovement> findAll() {
        return inventoryMovementRepository.findAll();
    }
}
