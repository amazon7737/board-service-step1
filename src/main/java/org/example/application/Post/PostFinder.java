package org.example.application.Post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostFinder {
    private final PostRepository postRepository;

    public PostFinder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> find(String sort) {
        return postRepository.findAll(sort);
    }

    public List<Post> findByUserId(Long userId, String sort) {
        return postRepository.findByUserId(userId, sort);
    }
    
}
