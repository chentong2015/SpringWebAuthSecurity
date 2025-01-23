package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {

    // TODO. 设置URL访问的安全权限，确保用户的认证和授权
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/home").permitAll();
                    requests.requestMatchers("/admin").authenticated();
                    requests.requestMatchers("/admin").hasRole("ADMIN");
                    requests.anyRequest().authenticated();
                });
        return httpSecurity.build();
    }
}
