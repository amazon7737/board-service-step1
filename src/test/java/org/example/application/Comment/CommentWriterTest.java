package org.example.application.Comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Comment Writing and Ownership Management")
class CommentWriterTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentWriter commentWriter;

    @Test
    @DisplayName("Scenario: Successfully write a root comment")
    void writeRootComment() {
        // Given
        Long postId = 1L;
        Long userId = 10L;
        String content = "Hello world";

        // When
        commentWriter.write(postId, userId, null, content);

        // Then
        verify(commentRepository).save(postId, userId, null, content);
    }

    @Test
    @DisplayName("Scenario: Successfully write a reply to an existing comment")
    void writeReplyComment() {
        // Given
        Long postId = 1L;
        Long userId = 10L;
        Long parentId = 100L;
        String content = "Reply content";
        given(commentRepository.findById(parentId)).willReturn(Optional.of(new Comment(parentId, postId, 20L, null, "Parent", LocalDateTime.now())));

        // When
        commentWriter.write(postId, userId, parentId, content);

        // Then
        verify(commentRepository).save(postId, userId, parentId, content);
    }

    @Test
    @DisplayName("Scenario: Success updating own comment content")
    void updateOwnComment() {
        // Given
        Long commentId = 50L;
        Long userId = 10L;
        String newContent = "Updated content";
        Comment existingComment = new Comment(commentId, 1L, userId, null, "Old content", LocalDateTime.now());
        given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));

        // When
        commentWriter.update(commentId, userId, newContent);

        // Then
        verify(commentRepository).updateContent(commentId, newContent);
    }

    @Test
    @DisplayName("Scenario: Fail updating comment belonging to another user")
    void updateOtherComment() {
        // Given
        Long commentId = 50L;
        Long myUserId = 10L;
        Long otherUserId = 20L;
        Comment existingComment = new Comment(commentId, 1L, otherUserId, null, "Old content", LocalDateTime.now());
        given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));

        // When & Then
        assertThatThrownBy(() -> commentWriter.update(commentId, myUserId, "New content"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Permission denied");
    }

    @Test
    @DisplayName("Scenario: Successfully delete own comment")
    void deleteOwnComment() {
        // Given
        Long commentId = 50L;
        Long userId = 10L;
        Comment existingComment = new Comment(commentId, 1L, userId, null, "Content", LocalDateTime.now());
        given(commentRepository.findById(commentId)).willReturn(Optional.of(existingComment));

        // When
        commentWriter.delete(commentId, userId);

        // Then
        verify(commentRepository).deleteById(commentId);
    }
}
