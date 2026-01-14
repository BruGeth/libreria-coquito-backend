package com.coquito.backend.controller;

import com.coquito.backend.dto.sale.SaleRequest;
import com.coquito.backend.dto.sale.SaleResponse;
import com.coquito.backend.service.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponse registerSale(@Valid @RequestBody SaleRequest request) {
        return saleService.registerSale(request);
    }

    @GetMapping(version = "1")
    public List<SaleResponse> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        
        if (from != null && to != null) {
            return saleService.findBySaleDateBetween(from, to);
        }
        return saleService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public SaleResponse findById(@PathVariable Long id) {
        return saleService.findById(id);
    }

    @PatchMapping(value = "/{id}/cancel", version = "1")
    public SaleResponse cancelSale(@PathVariable Long id) {
        return saleService.cancelSale(id);
    }
}
