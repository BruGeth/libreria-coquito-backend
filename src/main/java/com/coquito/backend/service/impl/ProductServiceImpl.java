package com.coquito.backend.service.impl;

import com.coquito.backend.entity.Product;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setCategory(product.getCategory());
        return productRepository.save(existing);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(findById(id));
    }
}
