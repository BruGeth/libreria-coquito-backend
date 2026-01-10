package com.coquito.backend.controller;

import com.coquito.backend.entity.StoreService;
import com.coquito.backend.service.StoreServiceManager;
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
    public StoreService create(@RequestBody StoreService service) {
        return storeServiceManager.create(service);
    }

    @GetMapping(version = "1")
    public List<StoreService> findAll() {
        return storeServiceManager.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public StoreService findById(@PathVariable Long id) {
        return storeServiceManager.findById(id);
    }

    @PutMapping(value = "/{id}", version = "1")
    public StoreService update(
            @PathVariable Long id,
            @RequestBody StoreService service) {
        return storeServiceManager.update(id, service);
    }

    @DeleteMapping(value = "/{id}", version = "1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeServiceManager.delete(id);
    }
}
