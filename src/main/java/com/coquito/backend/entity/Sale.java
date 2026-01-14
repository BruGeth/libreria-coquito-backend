package com.coquito.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime saleDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleItem> items = new ArrayList<>();

    private String notes;

    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
        DEBIT_CARD,
        TRANSFER,
        OTHER
    }

    public enum SaleStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    public void addItem(SaleItem item) {
        items.add(item);
        item.setSale(this);
    }

    public void removeItem(SaleItem item) {
        items.remove(item);
        item.setSale(null);
    }

    public void calculateTotal() {
        this.total = items.stream()
                .map(SaleItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
