package org.example.application.View;

import org.example.application.Auth.LoginedUser;
import org.example.application.Auth.SessionManager;
import org.example.application.Auth.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthViewController {

    private final UserService userService;
    private final SessionManager sessionManager;

    public AuthViewController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam Long id, @RequestParam String pw, Model model) {
        try {
            LoginedUser user = userService.login(id, pw);
            sessionManager.login(user); 
            return "redirect:/board";
        } catch (Exception e) {
            model.addAttribute("error", "잘못된 ID 또는 비밀번호입니다");
            return "auth/login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        userService.signup(username, email, password);
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout() {
        sessionManager.logout();
        return "redirect:/login";
    }
}
