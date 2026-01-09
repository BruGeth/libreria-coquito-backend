package com.coquito.backend.repository;

import com.coquito.backend.entity.StoreService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StoreServiceRepository extends JpaRepository<StoreService, Long> {
    List<StoreService> findByActiveTrue();
}
