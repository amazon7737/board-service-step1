package org.example.application.Post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostWriter {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final org.example.application.Comment.CommentRepository commentRepository;
    private final BookmarkRepository bookmarkRepository;

    public PostWriter(PostRepository postRepository, 
                      PostCategoryRepository postCategoryRepository,
                      org.example.application.Comment.CommentRepository commentRepository,
                      BookmarkRepository bookmarkRepository) {
        this.postRepository = postRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.commentRepository = commentRepository;
        this.bookmarkRepository = bookmarkRepository;
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

    @Transactional
    public void update(Long postId, Long userId, String title, String content, List<Long> categoryIds) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("Permission denied: You can only edit your own posts");
        }

        postRepository.update(postId, title, content);

        if (categoryIds != null) {
            postCategoryRepository.deleteByPostId(postId);
            for (Long categoryId : categoryIds) {
                postCategoryRepository.add(postId, categoryId);
            }
        }
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("Permission denied: You can only delete your own posts");
        }

        commentRepository.deleteByPostId(postId);
        bookmarkRepository.deleteByPostId(postId);
        postCategoryRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }
}
