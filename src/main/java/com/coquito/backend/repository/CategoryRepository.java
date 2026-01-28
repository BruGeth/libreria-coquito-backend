package com.coquito.backend.repository;

import com.coquito.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
    
    // Filtrar categorías por estado activo/inactivo
    List<Category> findByActive(Boolean active);
    
    // Buscar categoría activa por ID (para validaciones)
    Optional<Category> findByIdAndActiveTrue(Long id);
    
    // Verificar si una categoría tiene productos asociados
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.category.id = :categoryId")
    boolean hasAssociatedProducts(Long categoryId);
    
    // Contar productos activos asociados a una categoría
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId AND p.active = true")
    long countActiveProductsByCategory(Long categoryId);
}
