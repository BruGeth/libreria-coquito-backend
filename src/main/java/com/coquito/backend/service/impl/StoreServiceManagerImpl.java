package com.coquito.backend.service.impl;

import com.coquito.backend.dto.storeservice.StoreServiceMapper;
import com.coquito.backend.dto.storeservice.StoreServiceRequest;
import com.coquito.backend.dto.storeservice.StoreServiceResponse;
import com.coquito.backend.entity.StoreService;
import com.coquito.backend.repository.StoreServiceRepository;
import com.coquito.backend.service.StoreServiceManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceManagerImpl implements StoreServiceManager {

    private final StoreServiceRepository serviceRepository;
    private final StoreServiceMapper serviceMapper;

    @Override
    public StoreServiceResponse create(StoreServiceRequest request) {
        StoreService service = serviceMapper.toEntity(request);
        StoreService saved = serviceRepository.save(service);
        return serviceMapper.toResponse(saved);
    }

    @Override
    public StoreServiceResponse update(Long id, StoreServiceRequest request) {
        StoreService existing = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        serviceMapper.updateEntity(existing, request);
        StoreService updated = serviceRepository.save(existing);
        return serviceMapper.toResponse(updated);
    }

    @Override
    public StoreServiceResponse findById(Long id) {
        StoreService service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        return serviceMapper.toResponse(service);
    }

    @Override
    public List<StoreServiceResponse> findAll() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        StoreService service = serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
        serviceRepository.delete(service);
    }
}
