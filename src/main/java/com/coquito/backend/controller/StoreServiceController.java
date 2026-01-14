package com.coquito.backend.controller;

import com.coquito.backend.dto.storeservice.StoreServiceRequest;
import com.coquito.backend.dto.storeservice.StoreServiceResponse;
import com.coquito.backend.service.StoreServiceManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class StoreServiceController {

    private final StoreServiceManager storeServiceManager;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public StoreServiceResponse create(@Valid @RequestBody StoreServiceRequest request) {
        return storeServiceManager.create(request);
    }

    @GetMapping(version = "1")
    public List<StoreServiceResponse> findAll() {
        return storeServiceManager.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public StoreServiceResponse findById(@PathVariable Long id) {
        return storeServiceManager.findById(id);
    }

    @PutMapping(value = "/{id}", version = "1")
    public StoreServiceResponse update(
            @PathVariable Long id,
            @Valid @RequestBody StoreServiceRequest request) {
        return storeServiceManager.update(id, request);
    }

    @DeleteMapping(value = "/{id}", version = "1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeServiceManager.delete(id);
    }
}
