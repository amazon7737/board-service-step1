package org.example.application.Comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentWriter {
    private final CommentRepository commentRepository;

    public CommentWriter(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public void write(Long postId, Long userId, Long parentId, String content) {
        if (parentId != null) {
            commentRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }
        commentRepository.save(postId, userId, parentId, content);
    }

    @Transactional
    public void update(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("Permission denied: You can only edit your own comments");
        }

        commentRepository.updateContent(commentId, content);
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("Permission denied: You can only delete your own comments");
        }

        commentRepository.deleteById(commentId);
    }
}
