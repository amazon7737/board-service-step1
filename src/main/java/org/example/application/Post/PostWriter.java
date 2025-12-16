package org.example.application.Post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostWriter {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    public PostWriter(PostRepository postRepository, PostCategoryRepository postCategoryRepository) {
        this.postRepository = postRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Transactional
    public void write(Long userId, String title, String content, List<Long> categoryIds) {
        long postId = postRepository.save(userId, title, content);
        
        if (categoryIds != null) {
            for (Long categoryId : categoryIds) {
                postCategoryRepository.add(postId, categoryId);
            }
        }
    }
}
