package com.coquito.backend.service.impl;

import com.coquito.backend.dto.stockmovement.StockMovementMapper;
import com.coquito.backend.dto.stockmovement.StockMovementRequest;
import com.coquito.backend.dto.stockmovement.StockMovementResponse;
import com.coquito.backend.entity.Product;
import com.coquito.backend.entity.StockMovement;
import com.coquito.backend.exception.BusinessRuleViolationException;
import com.coquito.backend.exception.InsufficientStockException;
import com.coquito.backend.exception.ResourceNotFoundException;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.repository.StockMovementRepository;
import com.coquito.backend.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final StockMovementMapper stockMovementMapper;

    @Override
    @Transactional
    public StockMovementResponse registerMovement(StockMovementRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con ID: " + request.getProductId()));

        return registerMovement(
                product,
                request.getType(),
                request.getQuantity(),
                request.getReason(),
                null,
                null
        );
    }

    @Override
    @Transactional
    public StockMovementResponse registerMovement(Product product, StockMovement.MovementType type,
                                                   Integer quantity, String reason, Long referenceId,
                                                   String referenceType) {
        // Validar cantidad
        if (quantity <= 0) {
            throw new BusinessRuleViolationException("La cantidad debe ser mayor a 0");
        }

        // Calcular nuevo stock
        Integer previousStock = product.getStock();
        Integer newStock;

        switch (type) {
            case IN:
                newStock = previousStock + quantity;
                break;
            case OUT:
                if (previousStock < quantity) {
                    throw new InsufficientStockException(product.getName(), quantity, previousStock);
                }
                newStock = previousStock - quantity;
                break;
            case ADJUST:
                newStock = quantity; // En ajuste, quantity es el nuevo valor total
                quantity = Math.abs(newStock - previousStock); // Recalcular la diferencia
                break;
            default:
                throw new BusinessRuleViolationException("Tipo de movimiento no válido");
        }

        // Actualizar stock del producto
        product.setStock(newStock);
        productRepository.save(product);

        // Crear movimiento
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(type);
        movement.setQuantity(quantity);
        movement.setPreviousStock(previousStock);
        movement.setNewStock(newStock);
        movement.setMovementDate(LocalDateTime.now());
        movement.setReason(reason);
        movement.setReferenceId(referenceId);
        movement.setReferenceType(referenceType);

        StockMovement savedMovement = stockMovementRepository.save(movement);
        return stockMovementMapper.toResponse(savedMovement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> findAll() {
        List<StockMovement> movements = stockMovementRepository.findAll();
        return stockMovementMapper.toResponseList(movements);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> findByProductId(Long productId) {
        // Validar que el producto existe
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        List<StockMovement> movements = stockMovementRepository.findByProductId(productId);
        return stockMovementMapper.toResponseList(movements);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockMovementResponse> findByDateRange(LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new BusinessRuleViolationException(
                    "La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
        List<StockMovement> movements = stockMovementRepository.findByMovementDateBetween(from, to);
        return stockMovementMapper.toResponseList(movements);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateStockAvailability(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        if (product.getStock() < quantity) {
            throw new InsufficientStockException(product.getName(), quantity, product.getStock());
        }
    }
}
