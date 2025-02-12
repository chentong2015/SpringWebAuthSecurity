package org.example.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 配置文件设置是否需要Token验证
    @Value("${security.enabled:#{true}}")
    private boolean securityEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (securityEnabled) {
            String header = request.getHeader("Authorization");
            if (header == null) {
                request.setAttribute("TokenError", "Request is missing Authorization Bearer Token.");
                throw new AuthenticationCredentialsNotFoundException("Not Found Bearer Token.");
            }
        }

        // TODO. 创建默认能够被认证成功的AuthenticationToken对象, 调用方法super.setAuthenticated(true);
        UsernamePasswordAuthenticationToken authenticationDefault =
                new UsernamePasswordAuthenticationToken("DefaultUser", null, null);
        authenticationDefault.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationDefault);

        filterChain.doFilter(request, response);
    }
}
