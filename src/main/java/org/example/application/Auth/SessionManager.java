package org.example.application.Auth;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    private static final String SESSION_KEY = "LOGIN_USER";
    private final HttpSession session;

    public SessionManager(HttpSession session) {
        this.session = session;
    }

    public void login(LoginedUser user) {
        session.setAttribute(SESSION_KEY, user);
    }

    public LoginedUser validate() {
        return (LoginedUser) session.getAttribute(SESSION_KEY);
    }

    public void logout() {
        session.invalidate();
    }
}
