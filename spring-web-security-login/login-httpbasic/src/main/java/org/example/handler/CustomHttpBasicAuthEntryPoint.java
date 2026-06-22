package org.example.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomHttpBasicAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // Auth失败时默认设置返回Header, 并显示弹框
        response.addHeader("WWW-Authenticate", "Basic");
        response.setHeader("WWW-Authenticate", "Basic realm=\"Realm\"");

        // 如果直接401认证错误, 等效取消popup弹框效果
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
