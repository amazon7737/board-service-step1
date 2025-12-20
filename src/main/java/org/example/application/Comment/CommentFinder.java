package org.example.application.Comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentFinder {
    private final CommentRepository commentRepository;

    public CommentFinder(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
