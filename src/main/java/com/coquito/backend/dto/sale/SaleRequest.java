package com.coquito.backend.dto.sale;

import com.coquito.backend.entity.Sale;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest {

    @NotNull(message = "Payment method is required")
    private Sale.PaymentMethod paymentMethod;

    @NotEmpty(message = "Sale must have at least one item")
    @Valid
    private List<SaleItemRequest> items;

    private String notes;
}
