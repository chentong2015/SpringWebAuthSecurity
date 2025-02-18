package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 配置文件设置是否需要Token验证
    @Value("${security.enabled:#{true}}")
    private boolean securityEnabled;

    @Value("${security.testing:#{false}}")
    private boolean forSecurityTest;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (securityEnabled) {
            String header = request.getHeader("Authorization");
            if (header == null) {
                request.setAttribute("TokenError", "Request is missing Authorization Bearer Token.");
                throw new AuthenticationCredentialsNotFoundException("Not Found Bearer Token.");
            }
        }
        // 针对测试情况不做任何Authentication定制化
        if (forSecurityTest) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = buildAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private Authentication buildAuthentication(HttpServletRequest request) {
        AbstractAuthenticationToken authentication;
        String pathUrl = request.getRequestURL().toString();
        if (pathUrl.endsWith("/test2")) {
            // 需要认证成功，不需要ROLE
            authentication = new PreAuthenticatedAuthenticationToken("userEntity", null, null);
        } else if (pathUrl.endsWith("/test3")) {
            // 需要ROLE USER
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authentication = new PreAuthenticatedAuthenticationToken("userEntity", null, authorities);
        } else if (pathUrl.endsWith("/test4")) {
            // 需要ROLE ADMIN
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authentication = new PreAuthenticatedAuthenticationToken("userEntity", null, authorities);
        } else {
            // 默认创建认证成功的Authentication对象，但是不赋予角色
            // 如果访问带有ROLE要求的API，则403 Forbidden Error
            authentication = new OneTimeTokenAuthenticationToken(null, new ArrayList<>());
        }

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}
