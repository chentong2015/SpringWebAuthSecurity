package com.security.login.config;

import com.security.login.handler.CustomAuthSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// @Configuration
// @EnableWebSecurity
public class HttpBasicLoginConfig {

    // @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 每个Matchers匹配都需要设置对应的授权方式
                .antMatchers("/login").permitAll()
                .antMatchers("/home").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .anyRequest().authenticated()
                .and().httpBasic();
        return http.build();
    }

    // @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }
}
