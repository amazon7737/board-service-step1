package org.example.application.Post;

import org.example.application.Comment.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Post Writing and Cascade Deletion")
class PostWriterTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostCategoryRepository postCategoryRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookmarkRepository bookmarkRepository;

    @InjectMocks
    private PostWriter postWriter;

    @Test
    @DisplayName("Scenario: Successfully write a post with categories")
    void writePostWithCategories() {
        // Given
        Long userId = 1L;
        String title = "Title";
        String content = "Content";
        List<Long> categoryIds = List.of(101L, 102L);
        given(postRepository.save(userId, title, content)).willReturn(500L);

        // When
        postWriter.write(userId, title, content, categoryIds);

        // Then
        verify(postRepository).save(userId, title, content);
        verify(postCategoryRepository).add(500L, 101L);
        verify(postCategoryRepository).add(500L, 102L);
    }

    @Test
    @DisplayName("Scenario: Successfully update post content and categories as owner")
    void updatePostAsOwner() {
        // Given
        Long postId = 500L;
        Long userId = 1L;
        Post existingPost = new Post(postId, userId, "Old Title", "Old Content", LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(existingPost));

        // When
        postWriter.update(postId, userId, "New Title", "New Content", List.of(201L));

        // Then
        verify(postRepository).update(postId, "New Title", "New Content");
        verify(postCategoryRepository).deleteByPostId(postId);
        verify(postCategoryRepository).add(postId, 201L);
    }

    @Test
    @DisplayName("Scenario: Fail updating post belonging to another user")
    void updatePostFailAsNonOwner() {
        // Given
        Long postId = 500L;
        Long myUserId = 1L;
        Long otherUserId = 2L;
        Post existingPost = new Post(postId, otherUserId, "Title", "Content", LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(existingPost));

        // When & Then
        assertThatThrownBy(() -> postWriter.update(postId, myUserId, "New Title", "New Content", null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Permission denied");
    }

    @Test
    @DisplayName("Scenario: Delete post as owner involves cleaning up associated data")
    void deletePostAsOwner() {
        // Given
        Long postId = 500L;
        Long userId = 1L;
        Post existingPost = new Post(postId, userId, "Title", "Content", LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(existingPost));

        // When
        postWriter.delete(postId, userId);

        // Then
        verify(commentRepository).deleteByPostId(postId);
        verify(bookmarkRepository).deleteByPostId(postId);
        verify(postCategoryRepository).deleteByPostId(postId);
        verify(postRepository).deleteById(postId);
    }
}
