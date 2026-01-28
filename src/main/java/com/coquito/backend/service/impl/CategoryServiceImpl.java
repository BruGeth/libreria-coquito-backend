package com.coquito.backend.service.impl;

import com.coquito.backend.dto.category.CategoryMapper;
import com.coquito.backend.dto.category.CategoryRequest;
import com.coquito.backend.dto.category.CategoryResponse;
import com.coquito.backend.entity.Category;
import com.coquito.backend.exception.BusinessRuleViolationException;
import com.coquito.backend.repository.CategoryRepository;
import com.coquito.backend.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        log.info("Creating new category: {}", request.getName());
        
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        
        log.info("Category created successfully with ID: {}", saved.getId());
        return categoryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        log.info("Updating category ID: {}", id);
        
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        
        categoryMapper.updateEntity(existing, request);
        Category updated = categoryRepository.save(existing);
        
        log.info("Category updated successfully: {}", updated.getName());
        return categoryMapper.toResponse(updated);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> findByActive(Boolean active) {
        log.info("Finding categories with active status: {}", active);
        
        return categoryRepository.findByActive(active).stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Attempting to delete (soft delete) category ID: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        
        // Verificar si ya está inactiva
        if (Boolean.FALSE.equals(category.getActive())) {
            log.warn("Category ID {} is already inactive", id);
            throw new BusinessRuleViolationException("Category is already inactive");
        }
        
        // Verificar productos asociados
        long activeProducts = categoryRepository.countActiveProductsByCategory(id);
        if (activeProducts > 0) {
            log.warn("Cannot deactivate category ID {}: has {} active products", id, activeProducts);
            throw new BusinessRuleViolationException(
                String.format("Cannot deactivate category: %d active product(s) are still using it. " +
                              "Please reassign or deactivate products first.", activeProducts)
            );
        }
        
        // Soft delete: marcar como inactiva
        category.setActive(false);
        categoryRepository.save(category);
        
        log.info("Category ID {} marked as inactive (soft deleted)", id);
    }

    @Override
    @Transactional
    public CategoryResponse toggleActive(Long id) {
        log.info("Toggling active status for category ID: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        
        boolean newStatus = !Boolean.TRUE.equals(category.getActive());
        
        // Si se está desactivando, validar productos
        if (!newStatus) {
            long activeProducts = categoryRepository.countActiveProductsByCategory(id);
            if (activeProducts > 0) {
                throw new BusinessRuleViolationException(
                    String.format("Cannot deactivate category: %d active product(s) are still using it", activeProducts)
                );
            }
        }
        
        category.setActive(newStatus);
        Category updated = categoryRepository.save(category);
        
        log.info("Category ID {} status changed to: {}", id, newStatus ? "ACTIVE" : "INACTIVE");
        return categoryMapper.toResponse(updated);
    }

    @Override
    public void validateCategoryIsActive(Long categoryId) {
        log.debug("Validating if category ID {} is active", categoryId);
        
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));
        
        if (Boolean.FALSE.equals(category.getActive())) {
            log.warn("Attempted to use inactive category ID: {}", categoryId);
            throw new BusinessRuleViolationException(
                String.format("Cannot use inactive category: '%s'. Please select an active category.", 
                              category.getName())
            );
        }
    }
}
