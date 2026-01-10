package com.coquito.backend.controller;

import com.coquito.backend.entity.Category;
import com.coquito.backend.service.CategoryService;
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
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @GetMapping(version = "1")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping(value = "/{id}", version = "1")
    public Category update(
            @PathVariable Long id,
            @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping(value = "/{id}", version = "1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
