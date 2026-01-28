package com.coquito.backend.controller;

import com.coquito.backend.dto.category.CategoryRequest;
import com.coquito.backend.dto.category.CategoryResponse;
import com.coquito.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }

    /**
     * GET /api/v1/categories - Lista todas las categorías
     * GET /api/v1/categories?active=true - Lista solo categorías activas
     * GET /api/v1/categories?active=false - Lista solo categorías inactivas
     */
    @GetMapping(version = "1")
    public List<CategoryResponse> findAll(
            @RequestParam(required = false) Boolean active) {
        
        if (active != null) {
            return categoryService.findByActive(active);
        }
        return categoryService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping(value = "/{id}", version = "1")
    public CategoryResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    /**
     * DELETE /api/v1/categories/{id}
     * Realiza soft delete (marca como inactive) en lugar de borrar físicamente
     * Valida que no haya productos activos asociados antes de desactivar
     */
    @DeleteMapping(value = "/{id}", version = "1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    /**
     * PATCH /api/v1/categories/{id}/toggle
     * Cambia rápidamente el estado activo/inactivo de una categoría
     * Ejemplo: Si está activa -> la desactiva, si está inactiva -> la activa
     */
    @PatchMapping(value = "/{id}/toggle", version = "1")
    public CategoryResponse toggleActive(@PathVariable Long id) {
        return categoryService.toggleActive(id);
    }
}
