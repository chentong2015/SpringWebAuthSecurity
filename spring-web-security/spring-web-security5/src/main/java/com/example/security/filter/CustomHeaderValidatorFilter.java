package com.example.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomHeaderValidatorFilter extends OncePerRequestFilter {

    // 定义哪些URL需要被过滤
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/library/books/all");
    }

    // 过滤Request请求的信息，包括判断Header头部信息
    // 如果出错则定义返回的Response结果中的错误信息，反之执行执行FilterChain中的下一个Filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("X-Application-Name") == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getOutputStream().println(new ObjectMapper().writeValueAsString("header error"));
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
