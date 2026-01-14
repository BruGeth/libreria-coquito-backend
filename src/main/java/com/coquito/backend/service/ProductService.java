package com.coquito.backend.service;

import com.coquito.backend.dto.product.ProductRequest;
import com.coquito.backend.dto.product.ProductResponse;
import java.util.List;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    ProductResponse findById(Long id);

    List<ProductResponse> findAll();

    void delete(Long id);
}
