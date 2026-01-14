package com.coquito.backend.service;

import com.coquito.backend.dto.storeservice.StoreServiceRequest;
import com.coquito.backend.dto.storeservice.StoreServiceResponse;
import java.util.List;

public interface StoreServiceManager {

    StoreServiceResponse create(StoreServiceRequest request);

    StoreServiceResponse update(Long id, StoreServiceRequest request);

    StoreServiceResponse findById(Long id);

    List<StoreServiceResponse> findAll();

    void delete(Long id);
}
