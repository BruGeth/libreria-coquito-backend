package com.coquito.backend.dto.sale;

import com.coquito.backend.entity.Sale;
import com.coquito.backend.entity.SaleItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleMapper {

    public SaleResponse toResponse(Sale sale) {
        if (sale == null) {
            return null;
        }

        return SaleResponse.builder()
                .id(sale.getId())
                .saleDate(sale.getSaleDate())
                .total(sale.getTotal())
                .paymentMethod(sale.getPaymentMethod())
                .status(sale.getStatus())
                .items(toItemResponseList(sale.getItems()))
                .notes(sale.getNotes())
                .createdAt(sale.getCreatedAt())
                .build();
    }

    public SaleItemResponse toItemResponse(SaleItem item) {
        if (item == null) {
            return null;
        }

        return SaleItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubtotal())
                .build();
    }

    public List<SaleItemResponse> toItemResponseList(List<SaleItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());
    }

    public List<SaleResponse> toResponseList(List<Sale> sales) {
        if (sales == null) {
            return null;
        }
        return sales.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
