package org.example.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Calendar;

// 用户身份认证失败后的处理器
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        String output = String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime());
        response.getOutputStream().println(output);
    }
}


