package org.example.application.View;

import org.example.application.Auth.LoginedUser;
import org.example.application.Auth.SessionManager;
import org.example.application.Post.Post;
import org.example.application.Post.PostFinder;
import org.example.application.Post.PostWriter;
import org.example.application.Comment.Comment;
import org.example.application.Comment.CommentFinder;
import org.example.application.Comment.CommentWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardViewController {

    private final PostFinder postFinder;
    private final PostWriter postWriter;
    private final SessionManager sessionManager;
    private final CommentFinder commentFinder;
    private final CommentWriter commentWriter;

    public BoardViewController(PostFinder postFinder, PostWriter postWriter, SessionManager sessionManager, 
                               CommentFinder commentFinder, CommentWriter commentWriter) {
        this.postFinder = postFinder;
        this.postWriter = postWriter;
        this.sessionManager = sessionManager;
        this.commentFinder = commentFinder;
        this.commentWriter = commentWriter;
    }

    @GetMapping
    public String list(Model model) {
        List<Post> posts = postFinder.find("DESC");
        model.addAttribute("posts", posts);
        
        LoginedUser user = sessionManager.validate();
        model.addAttribute("isLoggedIn", user != null);
        if (user != null) {
            model.addAttribute("user", user);
        }
        
        return "board/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Post post = postFinder.findById(id);
        model.addAttribute("post", post);
        
        // 댓글 목록 조회
        List<Comment> comments = commentFinder.findByPostId(id);
        model.addAttribute("comments", comments);
        
        LoginedUser user = sessionManager.validate();
        model.addAttribute("isLoggedIn", user != null);
        if (user != null) {
            model.addAttribute("currentUserId", user.userId());
        }
        
        return "board/detail";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        LoginedUser user = sessionManager.validate();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "board/form";
    }

    @PostMapping("/write")
    public String write(@RequestParam String title, @RequestParam String content) {
        LoginedUser user = sessionManager.validate();
        if (user == null) {
            return "redirect:/login";
        }
        
        // UI에서 카테고리 선택 기능이 구현되지 않아 빈 카테고리 리스트 전달
        postWriter.write(user.userId(), title, content, Collections.emptyList());
        
        return "redirect:/board";
    }
    
    @PostMapping("/{postId}/comments")
    public String addComment(@PathVariable Long postId, 
                            @RequestParam String content,
                            @RequestParam(required = false) Long parentId) {
        LoginedUser user = sessionManager.validate();
        if (user == null) {
            return "redirect:/login";
        }
        
        commentWriter.write(postId, user.userId(), parentId, content);
        return "redirect:/board/" + postId;
    }
    
    @PostMapping("/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long commentId, @RequestParam Long postId) {
        LoginedUser user = sessionManager.validate();
        if (user == null) {
            return "redirect:/login";
        }
        
        commentWriter.delete(commentId, user.userId());
        return "redirect:/board/" + postId;
    }
}
