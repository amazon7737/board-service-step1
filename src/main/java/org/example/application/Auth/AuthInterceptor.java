package org.example.application.Auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final SessionManager sessionManager;

    public AuthInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        LoginedUser user = sessionManager.validate();

        if (uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs")) {
            if (user == null || !"ADMIN".equals(user.role())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                return false;
            }
        }
        return true;
    }
}
