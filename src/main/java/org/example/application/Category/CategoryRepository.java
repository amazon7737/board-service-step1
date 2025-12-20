package org.example.application.Category;

import java.util.List;

public interface CategoryRepository {
    void save(String name);
    List<Category> findAll();
}
