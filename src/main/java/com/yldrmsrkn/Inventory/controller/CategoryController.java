package com.yldrmsrkn.Inventory.controller;

import com.yldrmsrkn.Inventory.entity.Category;
import com.yldrmsrkn.Inventory.exception.CategoryNotFoundException;
import com.yldrmsrkn.Inventory.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id") Long categoryId) throws CategoryNotFoundException {
        Category category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable(value = "id") Long cagegoryId, @RequestBody Category categoryDetails) throws CategoryNotFoundException {
        Category updatedCategory = categoryService.updateCategory(cagegoryId, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

}