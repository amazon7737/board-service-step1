package org.example.application.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    long save(Long userId, String title, String content);
    Optional<Post> findById(Long id);
    List<Post> findAll(String sort);
    List<Post> findByUserId(Long userId);
    List<Post> findByUserId(Long userId, String sort);
    void update(Long id, String title, String content);
    void deleteById(Long id);
}
