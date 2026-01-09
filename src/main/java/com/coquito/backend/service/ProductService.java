package com.coquito.backend.service;

import com.coquito.backend.entity.Product;
import java.util.List;

public interface ProductService {

    Product create(Product product);

    Product update(Long id, Product product);

    Product findById(Long id);

    List<Product> findAll();

    void delete(Long id);
}
