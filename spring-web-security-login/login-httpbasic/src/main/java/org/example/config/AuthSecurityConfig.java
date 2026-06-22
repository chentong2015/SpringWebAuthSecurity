package org.example.config;

import org.example.handler.CustomHttpBasicAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {

    @Autowired
    private CustomHttpBasicAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        System.out.println("AuthSecurityConfig load");
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/public").permitAll();
                    requests.anyRequest().authenticated();
                })
                // .httpBasic(Customizer.withDefaults()) 默认Popup表单验证 VS 取消弹框
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.authEntryPoint))
                .build();
    }
}
