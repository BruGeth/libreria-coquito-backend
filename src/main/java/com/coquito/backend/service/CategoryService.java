package com.coquito.backend.service;

import com.coquito.backend.dto.category.CategoryRequest;
import com.coquito.backend.dto.category.CategoryResponse;
import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();
    
    // Filtrar categorías por estado activo
    List<CategoryResponse> findByActive(Boolean active);

    // Soft delete: marca como inactiva en lugar de borrar
    void delete(Long id);
    
    // Toggle: cambiar estado activo/inactivo rápidamente
    CategoryResponse toggleActive(Long id);
    
    // Validar si una categoría puede ser usada (está activa)
    void validateCategoryIsActive(Long categoryId);
}
