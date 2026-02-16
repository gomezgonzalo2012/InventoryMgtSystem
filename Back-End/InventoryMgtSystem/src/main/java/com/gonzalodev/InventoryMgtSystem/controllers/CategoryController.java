package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.CategoryDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> saveCategory(@RequestBody @Valid CategoryDTO category) {
        Response response = categoryService.createCategory(category);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO category) {
        Response response = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllCategories() {
        Response response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getCategoryById(@PathVariable Long id) {
        Response response = categoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long id) {
        Response response = categoryService.deleteCategory(id);
        return ResponseEntity.ok(response);
    }
}