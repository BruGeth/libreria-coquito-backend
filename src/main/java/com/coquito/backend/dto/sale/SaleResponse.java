package com.coquito.backend.dto.sale;

import com.coquito.backend.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long id;
    private LocalDateTime saleDate;
    private BigDecimal total;
    private Sale.PaymentMethod paymentMethod;
    private Sale.SaleStatus status;
    private List<SaleItemResponse> items;
    private String notes;
    private LocalDateTime createdAt;
}
