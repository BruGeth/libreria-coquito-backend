package com.coquito.backend.service.impl;

import com.coquito.backend.dto.product.ProductMapper;
import com.coquito.backend.dto.product.ProductRequest;
import com.coquito.backend.dto.product.ProductResponse;
import com.coquito.backend.entity.Category;
import com.coquito.backend.entity.Product;
import com.coquito.backend.repository.CategoryRepository;
import com.coquito.backend.repository.ProductRepository;
import com.coquito.backend.service.CategoryService;
import com.coquito.backend.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        log.info("Creating new product: {}", request.getName());
        
        // ✅ VALIDACIÓN: Verificar que la categoría esté activa
        categoryService.validateCategoryIsActive(request.getCategoryId());
        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        
        Product product = productMapper.toEntity(request, category);
        Product saved = productRepository.save(product);
        
        log.info("Product created successfully with ID: {}", saved.getId());
        return productMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        log.info("Updating product ID: {}", id);
        
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        
        // ✅ VALIDACIÓN: Si está cambiando de categoría, verificar que la nueva esté activa
        if (!existing.getCategory().getId().equals(request.getCategoryId())) {
            log.info("Category change detected for product ID {}, validating new category", id);
            categoryService.validateCategoryIsActive(request.getCategoryId());
        }
        
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        
        productMapper.updateEntity(existing, request, category);
        Product updated = productRepository.save(existing);
        
        log.info("Product updated successfully: {}", updated.getName());
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
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
