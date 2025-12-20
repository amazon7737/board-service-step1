package org.example.application.Post;

import java.util.List;

public interface PostCategoryRepository {
    void add(Long postId, Long categoryId);
    List<Long> findCategoryIdsByPostId(Long postId);
    void delete(Long postId, Long categoryId);
    void deleteByPostId(Long postId);
}
