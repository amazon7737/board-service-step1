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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String password;
        private String name;
        private String email;
        private LocalDateTime createdAt;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(id, password, name, email, createdAt);
        }
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
