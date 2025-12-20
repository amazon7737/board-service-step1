package org.example.application.Post;

import java.util.List;

public interface BookmarkRepository {
    void save(Long userId, Long postId);
    boolean exists(Long userId, Long postId);
    List<Long> findPostIdsByUserId(Long userId);
    void delete(Long userId, Long postId);
    void deleteByPostId(Long postId);
}
