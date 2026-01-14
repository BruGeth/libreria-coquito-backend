package com.coquito.backend.service;

import com.coquito.backend.dto.sale.SaleRequest;
import com.coquito.backend.dto.sale.SaleResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {

    SaleResponse registerSale(SaleRequest request);

    SaleResponse findById(Long id);

    List<SaleResponse> findAll();

    List<SaleResponse> findBySaleDateBetween(LocalDateTime from, LocalDateTime to);

    SaleResponse cancelSale(Long id);
}
