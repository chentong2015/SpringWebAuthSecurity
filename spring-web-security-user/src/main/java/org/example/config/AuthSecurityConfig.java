package org.example.config;

import org.example.filter.AuthEntryPointHandler;
import org.example.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 1. 激活在方法层面的权限授权配置
public class AuthSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPointHandler authEntryPointHandler;

    // 2. 设置在SecurityFilter过滤层面的权限配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                // 授权异常的处理器(处理401 Unauthorized请求)
                .exceptionHandling(exceptionHandler ->
                        exceptionHandler.authenticationEntryPoint(authEntryPointHandler))

                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)

                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/home").permitAll();
                    requests.requestMatchers("/user").authenticated();
                    requests.requestMatchers("/user").hasRole("USER");
                    requests.requestMatchers("/user").hasAuthority("TEST");

                    requests.requestMatchers("/admin").authenticated();
                    requests.requestMatchers("/admin").hasRole("ADMIN");
                    requests.requestMatchers("/limit").denyAll();
                    requests.anyRequest().authenticated();
                });

        // 没有提供Form Login表单, 需要请求后端Endpoint来完成登录
        return httpSecurity.build();
    }
}