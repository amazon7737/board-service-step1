package org.example.application.Category;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryManager {
    private final CategoryRepository categoryRepository;

    public CategoryManager(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void add(String name) {
        categoryRepository.save(name);
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }
}
