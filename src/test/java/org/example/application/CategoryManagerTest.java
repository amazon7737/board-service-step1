package org.example.application;

import org.example.application.Category.CategoryManager;
import org.example.application.Category.Category;
import org.example.application.Category.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Category Management")
class CategoryManagerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryManager categoryManager;

    @Test
    @DisplayName("Scenario: Add a new category")
    void addCategory() {
        // Given
        String categoryName = "Technology";

        // When
        categoryManager.add(categoryName);

        // Then
        verify(categoryRepository).save(categoryName);
    }

    @Test
    @DisplayName("Scenario: List all categories")
    void findCategories() {
        // Given
        Category cat1 = new Category(1L, "Tech", LocalDateTime.now());
        Category cat2 = new Category(2L, "Life", LocalDateTime.now());
        given(categoryRepository.findAll()).willReturn(List.of(cat1, cat2));

        // When
        List<Category> result = categoryManager.findCategories();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Category::getName).contains("Tech", "Life");
    }
}
