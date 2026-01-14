package com.coquito.backend.service;

import com.coquito.backend.dto.category.CategoryRequest;
import com.coquito.backend.dto.category.CategoryResponse;
import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    void delete(Long id);
}
