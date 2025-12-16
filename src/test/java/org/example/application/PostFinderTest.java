package org.example.application;

import org.example.application.Post.PostFinder;
import org.example.application.Post.Post;
import org.example.application.Post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Post Finding Feature")
class PostFinderTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostFinder postFinder;

    @Test
    @DisplayName("Scenario: Find all posts sorted by descending order")
    void findAllPostsSortedDesc() {
        // Given
        String sort = "DESC";
        Post post1 = new Post(1L, 1L, "Title1", "Content1", LocalDateTime.now());
        Post post2 = new Post(2L, 2L, "Title2", "Content2", LocalDateTime.now().minusHours(1));
        List<Post> expectedPosts = List.of(post1, post2);

        given(postRepository.findAll(sort)).willReturn(expectedPosts);

        // When
        List<Post> result = postFinder.find(sort);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(post1);
        verify(postRepository).findAll(sort);
    }

    @Test
    @DisplayName("Scenario: Find posts by specific user")
    void findPostsByUserId() {
        // Given
        Long userId = 1L;
        String sort = "ASC";
        Post post1 = new Post(1L, userId, "My Post", "My Content", LocalDateTime.now());
        List<Post> expectedPosts = List.of(post1);

        given(postRepository.findByUserId(userId, sort)).willReturn(expectedPosts);

        // When
        List<Post> result = postFinder.findByUserId(userId, sort);

        // Then
        assertThat(result).containsExactly(post1);
        verify(postRepository).findByUserId(userId, sort);
    }
}
