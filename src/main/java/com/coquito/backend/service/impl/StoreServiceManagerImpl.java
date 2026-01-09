package com.coquito.backend.service.impl;

import com.coquito.backend.entity.StoreService;
import com.coquito.backend.repository.StoreServiceRepository;
import com.coquito.backend.service.StoreServiceManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
    

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceManagerImpl implements StoreServiceManager {

    private final StoreServiceRepository serviceRepository;

    @Override
    public StoreService create(StoreService service) {
        return serviceRepository.save(service);
    }

    @Override
    public StoreService update(Long id, StoreService service) {
        StoreService existing = findById(id);
        existing.setName(service.getName());
        existing.setDescription(service.getDescription());
        existing.setPrice(service.getPrice());
        return serviceRepository.save(existing);
    }

    @Override
    public StoreService findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Service not found"));
    }

    @Override
    public List<StoreService> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        serviceRepository.delete(findById(id));
    }
}
