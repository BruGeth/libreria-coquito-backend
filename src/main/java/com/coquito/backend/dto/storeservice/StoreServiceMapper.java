package com.coquito.backend.dto.storeservice;

import com.coquito.backend.entity.StoreService;
import org.springframework.stereotype.Component;

@Component
public class StoreServiceMapper {

    public StoreService toEntity(StoreServiceRequest request) {
        StoreService service = new StoreService();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
        return service;
    }

    public StoreServiceResponse toResponse(StoreService service) {
        return StoreServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .price(service.getPrice())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }

    public void updateEntity(StoreService service, StoreServiceRequest request) {
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setPrice(request.getPrice());
    }
}
