package com.coquito.backend.service;

import com.coquito.backend.entity.StoreService;
import java.util.List;

public interface StoreServiceManager {

    StoreService create(StoreService service);

    StoreService update(Long id, StoreService service);

    StoreService findById(Long id);

    List<StoreService> findAll();

    void delete(Long id);
}
