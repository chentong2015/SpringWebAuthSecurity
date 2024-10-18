package org.example.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 配置Session相关属性的两种方式：
// 1. 通过SessionManagement来管理
// 2. 自定义有关Session的过滤器，配置过期时间以及安全性
@Configuration
@EnableWebSecurity
public class SessionConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // TODO. 添加的Filter必须带有Order先后顺序
        http.addFilterAfter(new CookieSessionFilter(), BasicAuthenticationFilter.class);
        http.csrf().disable().sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .invalidSessionUrl("/invalidSession")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));
        return http.build();
    }

    static class CookieSessionFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            Cookie[] allCookies = req.getCookies();
            if (allCookies != null) {
                Cookie session = getSessionFromCookies(allCookies);
                if (session != null) {
                    session.setMaxAge(100); // 100s
                    session.setHttpOnly(false);
                    session.setSecure(true);
                    res.addCookie(session);
                }
            }
            chain.doFilter(req, res);
        }

        private Cookie getSessionFromCookies(Cookie[] cookies) {
            return Arrays.stream(cookies).filter(x -> x.getName().equals("JSESSIONID"))
                    .findFirst().orElse(null);
        }
    }
}
