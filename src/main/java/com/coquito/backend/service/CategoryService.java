package com.coquito.backend.service;

import com.coquito.backend.entity.Category;
import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category update(Long id, Category category);

    Category findById(Long id);

    List<Category> findAll();

    void delete(Long id);
}
