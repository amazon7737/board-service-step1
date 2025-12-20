package org.example.application.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    void save(Long postId, Long userId, Long parentId, String content);
    List<Comment> findByPostId(Long postId);
    Optional<Comment> findById(Long id);
    void updateContent(Long id, String content);
    void deleteById(Long id);
    void deleteByPostId(Long postId);
}
