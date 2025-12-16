package org.example.application.Post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookmarkManager {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;

    public BookmarkManager(BookmarkRepository bookmarkRepository, PostRepository postRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void add(Long userId, Long postId) {
        if (!bookmarkRepository.exists(userId, postId)) {
            bookmarkRepository.save(userId, postId);
        }
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        bookmarkRepository.delete(userId, postId);
    }

    public List<Post> getBookmarkedPosts(Long userId) {
        List<Long> postIds = bookmarkRepository.findPostIdsByUserId(userId);
        List<Post> posts = new ArrayList<>();
        for (Long postId : postIds) {
            postRepository.findById(postId).ifPresent(posts::add);
        }
        return posts;
    }
}
