package org.example.application;

import org.example.application.Post.BookmarkManager;
import org.example.application.Post.Post;
import org.example.application.Post.BookmarkRepository;
import org.example.application.Post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Bookmark Management")
class BookmarkManagerTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private BookmarkManager bookmarkManager;

    @Test
    @DisplayName("Scenario: Successfully add a new bookmark")
    void addBookmarkWhenNotExists() {
        // Given
        Long userId = 1L;
        Long postId = 100L;
        given(bookmarkRepository.exists(userId, postId)).willReturn(false);

        // When
        bookmarkManager.add(userId, postId);

        // Then
        verify(bookmarkRepository).save(userId, postId);
    }

    @Test
    @DisplayName("Scenario: Do nothing if bookmark already exists")
    void doNotAddBookmarkWhenExists() {
        // Given
        Long userId = 1L;
        Long postId = 100L;
        given(bookmarkRepository.exists(userId, postId)).willReturn(true);

        // When
        bookmarkManager.add(userId, postId);

        // Then
        verify(bookmarkRepository, never()).save(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Scenario: Delete a bookmark")
    void deleteBookmark() {
        // Given
        Long userId = 1L;
        Long postId = 100L;

        // When
        bookmarkManager.delete(userId, postId);

        // Then
        verify(bookmarkRepository).delete(userId, postId);
    }

    @Test
    @DisplayName("Scenario: Retrieve all bookmarked posts details")
    void getBookmarkedPosts() {
        // Given
        Long userId = 1L;
        Long postId1 = 101L;
        Long postId2 = 102L;
        List<Long> bookmarkedPostIds = List.of(postId1, postId2);
        
        Post post1 = new Post(postId1, 2L, "P1", "C1", LocalDateTime.now());
        Post post2 = new Post(postId2, 3L, "P2", "C2", LocalDateTime.now());

        given(bookmarkRepository.findPostIdsByUserId(userId)).willReturn(bookmarkedPostIds);
        given(postRepository.findById(postId1)).willReturn(Optional.of(post1));
        given(postRepository.findById(postId2)).willReturn(Optional.of(post2));

        // When
        List<Post> results = bookmarkManager.getBookmarkedPosts(userId);

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).containsExactly(post1, post2);
    }
}
