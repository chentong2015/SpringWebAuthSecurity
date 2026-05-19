package org.example.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

// Access Denied / forbidden 禁止访问特定URL
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String requestUrl = request.getRequestURI();
            System.out.println( auth.getName() + " attempted to access the protected URL: " + requestUrl);
        }
        // 请求被阻止后跳转到特定的accessDenied页面
        response.sendRedirect(request.getContextPath() + "/accessDenied");
    }
}
