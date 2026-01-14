package com.coquito.backend.controller;

import com.coquito.backend.dto.stockmovement.StockMovementRequest;
import com.coquito.backend.dto.stockmovement.StockMovementResponse;
import com.coquito.backend.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/stock/movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockService stockService;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementResponse registerMovement(@Valid @RequestBody StockMovementRequest request) {
        return stockService.registerMovement(request);
    }

    @GetMapping(version = "1")
    public List<StockMovementResponse> findAll(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (productId != null) {
            return stockService.findByProductId(productId);
        }
        
        if (from != null && to != null) {
            return stockService.findByDateRange(from, to);
        }
        
        return stockService.findAll();
    }
}
