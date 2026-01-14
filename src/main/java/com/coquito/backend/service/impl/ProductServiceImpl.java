package com.coquito.backend.service.impl;

import com.coquito.backend.dto.product.ProductMapper;
import com.coquito.backend.dto.product.ProductRequest;
import com.coquito.backend.dto.product.ProductResponse;
import com.coquito.backend.entity.Category;
import com.coquito.backend.entity.Product;
import com.coquito.backend.repository.CategoryRepository;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        
        Product product = productMapper.toEntity(request, category);
        Product saved = productRepository.save(product);
        return productMapper.toResponse(saved);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        
        productMapper.updateEntity(existing, request, category);
        Product updated = productRepository.save(existing);
        return productMapper.toResponse(updated);
    }

    @Override
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
