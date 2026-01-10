package com.coquito.backend.controller;

import com.coquito.backend.entity.Product;
import com.coquito.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(version = "1")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @GetMapping(version = "1")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(value = "/{id}", version = "1")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping(value = "/{id}", version = "1")
    public Product update(
            @PathVariable Long id,
            @RequestBody Product product) {
        return productService.update(id, product);
    }

    @DeleteMapping(value = "/{id}", version = "1")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
