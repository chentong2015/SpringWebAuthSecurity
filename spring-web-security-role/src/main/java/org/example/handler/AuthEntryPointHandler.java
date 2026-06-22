package org.example.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// TODO. 当用户访问需要Auth的Endpoint, 却没有提供Credentials
@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    // 授权异常的处理器(处理401 Unauthorized请求)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}