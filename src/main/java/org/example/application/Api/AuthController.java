package org.example.application.Api;

import org.example.application.Auth.LoginedUser;
import org.example.application.Auth.SessionManager;
import org.example.application.Auth.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthController {

    private final UserService userService;
    private final SessionManager sessionManager;

    public AuthController(UserService userService, SessionManager sessionManager) {
        this.userService = userService;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public String login(@RequestParam Long id, @RequestParam String pw) {
        LoginedUser user = userService.login(id, pw);
        sessionManager.login(user);
        return "Login Successful";
    }
    
    @PostMapping("/logout")
    public String logout() {
        sessionManager.logout();
        return "Logout Successful";
    }

    @PostMapping("/signup")
    public String signup(@org.springframework.web.bind.annotation.RequestBody org.example.application.Api.dto.UserSignupRequestDto request) {
        userService.signup(request.getUsername(), request.getEmail(), request.getPassword());
        return "Signup Successful";
    }
}
