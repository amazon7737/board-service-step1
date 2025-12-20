package org.example.application.Post;

import java.time.LocalDateTime;

public class Post {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public Post(Long id, Long userId, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {
        private Long id;
        private Long userId;
        private String title;
        private String content;
        private LocalDateTime createdAt;

        public PostBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Post build() {
            return new Post(id, userId, title, content, createdAt);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
