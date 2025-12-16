package org.example.application.Category;

import java.time.LocalDateTime;

public class Category {
    private final Long id;
    private final String name;
    private final LocalDateTime createdAt;


    public Category(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
