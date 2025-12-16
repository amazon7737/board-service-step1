package org.example.application;

import org.example.application.Post.PostWriter;
import org.example.application.Post.PostCategoryRepository;
import org.example.application.Post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Story: Post Writing Feature")
class PostWriterTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostCategoryRepository postCategoryRepository;

    @InjectMocks
    private PostWriter postWriter;

    @Test
    @DisplayName("Scenario: Successfully write a new post with categories")
    void writePostWithCategories() {
        // Given
        Long userId = 1L;
        String title = "New Post";
        String content = "Hello World";
        List<Long> categoryIds = List.of(10L, 20L);
        long generatedPostId = 100L;

        given(postRepository.save(eq(userId), eq(title), eq(content))).willReturn(generatedPostId);

        // When
        postWriter.write(userId, title, content, categoryIds);

        // Then
        verify(postRepository, times(1)).save(userId, title, content);
        verify(postCategoryRepository, times(1)).add(generatedPostId, 10L);
        verify(postCategoryRepository, times(1)).add(generatedPostId, 20L);
    }

    @Test
    @DisplayName("Scenario: Write a post without categories")
    void writePostWithoutCategories() {
        // Given
        Long userId = 1L;
        String title = "Simple Post";
        String content = "Just content";
        List<Long> categoryIds = null;
        long generatedPostId = 101L;

        given(postRepository.save(eq(userId), eq(title), eq(content))).willReturn(generatedPostId);

        // When
        postWriter.write(userId, title, content, categoryIds);

        // Then
        verify(postRepository, times(1)).save(userId, title, content);
        verify(postCategoryRepository, times(0)).add(eq(generatedPostId), org.mockito.ArgumentMatchers.anyLong());
    }
}
