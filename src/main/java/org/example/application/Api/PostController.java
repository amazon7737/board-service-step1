package org.example.application.Api;

import org.example.application.Api.dto.PostRequestDto;
import org.example.application.Api.dto.PostResponseDto;
import org.example.application.Post.Post;
import org.example.application.Post.PostFinder;
import org.example.application.Post.PostWriter;
import org.example.application.Auth.SessionManager;
import org.example.application.Auth.LoginedUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriter postWriter;
    private final PostFinder postFinder;
    private final SessionManager sessionManager;

    public PostController(PostWriter postWriter, PostFinder postFinder, SessionManager sessionManager) {
        this.postWriter = postWriter;
        this.postFinder = postFinder;
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public String create(@RequestBody PostRequestDto request) {
        LoginedUser user = sessionManager.validate();
        if (user == null) {
            throw new RuntimeException("Login required"); // Simple exception for now
        }

        postWriter.write(user.userId(), request.getTitle(), request.getContent(), request.getCategoryIds());
        return "Post created successfully";
    }

    @GetMapping
    public List<PostResponseDto> list(@RequestParam(required = false, defaultValue = "DESC") String sort) {
        List<Post> posts = postFinder.find(sort);
        return posts.stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostResponseDto get(@PathVariable Long id) {
        Post post = postFinder.findById(id);
        return new PostResponseDto(post);
    }
}
