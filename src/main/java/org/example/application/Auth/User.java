package org.example.application.Auth;

import java.time.LocalDateTime;

public class User {
    private final Long id;
    private final String password;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    public User(Long id, String password, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getPassword() {
        return password;
    }
}
