package com.coquito.backend.service.impl;

import com.coquito.backend.dto.category.CategoryMapper;
import com.coquito.backend.dto.category.CategoryRequest;
import com.coquito.backend.dto.category.CategoryResponse;
import com.coquito.backend.entity.Category;
import com.coquito.backend.repository.CategoryRepository;
import com.coquito.backend.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new IllegalArgumentException("Category already exists");
        }
        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        categoryMapper.updateEntity(existing, request);
        Category updated = categoryRepository.save(existing);
        return categoryMapper.toResponse(updated);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
