package com.yldrmsrkn.Inventory.service;

import com.yldrmsrkn.Inventory.entity.Category;
import com.yldrmsrkn.Inventory.exception.CategoryNotFoundException;
import com.yldrmsrkn.Inventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
    }

    public Category addCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(Long id, Category category) throws CategoryNotFoundException {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }
}
