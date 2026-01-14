package com.coquito.backend.service.impl;

import com.coquito.backend.dto.sale.SaleItemRequest;
import com.coquito.backend.dto.sale.SaleMapper;
import com.coquito.backend.dto.sale.SaleRequest;
import com.coquito.backend.dto.sale.SaleResponse;
import com.coquito.backend.entity.Product;
import com.coquito.backend.entity.Sale;
import com.coquito.backend.entity.SaleItem;
import com.coquito.backend.entity.StockMovement;
import com.coquito.backend.exception.BusinessRuleViolationException;
import com.coquito.backend.exception.InvalidSaleException;
import com.coquito.backend.exception.ResourceNotFoundException;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.repository.SaleRepository;
import com.coquito.backend.service.SaleService;
import com.coquito.backend.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final StockService stockService;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponse registerSale(SaleRequest request) {
        // Validar request
        validateSaleRequest(request);

        // Crear la venta
        Sale sale = new Sale();
        sale.setSaleDate(LocalDateTime.now());
        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setStatus(Sale.SaleStatus.PENDING);
        sale.setNotes(request.getNotes());

        // Procesar items
        for (SaleItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Producto no encontrado con ID: " + itemRequest.getProductId()));

            // Validar producto activo
            if (!product.getActive()) {
                throw new BusinessRuleViolationException(
                        "El producto '" + product.getName() + "' no está activo");
            }

            // Validar stock disponible
            stockService.validateStockAvailability(product.getId(), itemRequest.getQuantity());

            // Crear item de venta
            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(itemRequest.getQuantity());
            saleItem.setUnitPrice(product.getPrice());
            saleItem.calculateSubtotal();

            sale.addItem(saleItem);

            // Descontar stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);

            // Registrar movimiento de stock
            stockService.registerMovement(
                    product,
                    StockMovement.MovementType.OUT,
                    itemRequest.getQuantity(),
                    "Venta - ID pendiente",
                    null,
                    "SALE"
            );
        }

        // Calcular total
        sale.calculateTotal();
        sale.setStatus(Sale.SaleStatus.COMPLETED);

        // Guardar venta
        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toResponse(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponse findById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> findAll() {
        List<Sale> sales = saleRepository.findAllOrderByDateDesc();
        return saleMapper.toResponseList(sales);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SaleResponse> findBySaleDateBetween(LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to)) {
            throw new InvalidSaleException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
        List<Sale> sales = saleRepository.findBySaleDateBetween(from, to);
        return saleMapper.toResponseList(sales);
    }

    @Override
    @Transactional
    public SaleResponse cancelSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));

        if (sale.getStatus() == Sale.SaleStatus.CANCELLED) {
            throw new BusinessRuleViolationException("La venta ya está cancelada");
        }

        // Revertir stock
        for (SaleItem item : sale.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);

            // Registrar movimiento de ajuste
            stockService.registerMovement(
                    product,
                    StockMovement.MovementType.IN,
                    item.getQuantity(),
                    "Cancelación de venta #" + sale.getId(),
                    sale.getId(),
                    "SALE_CANCELLATION"
            );
        }

        sale.setStatus(Sale.SaleStatus.CANCELLED);
        Sale cancelledSale = saleRepository.save(sale);

        return saleMapper.toResponse(cancelledSale);
    }

    private void validateSaleRequest(SaleRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvalidSaleException("La venta debe tener al menos un item");
        }

        for (SaleItemRequest item : request.getItems()) {
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new InvalidSaleException("La cantidad debe ser mayor a 0");
            }
        }
    }
}
