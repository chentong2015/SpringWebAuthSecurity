package org.example.config;

import org.example.config.handler.CustomAuthFailureHandler;
import org.example.config.handler.CustomAuthSuccessHandler;
import org.example.config.handler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthHandlerSecurityConfig {

    // TODO. 使用Handler来完成页面的跳转, 不能和自动跳转链接同时使用
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
         .csrf(AbstractHttpConfigurer::disable)
         .authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/public").permitAll();
            requests.requestMatchers("/login").permitAll();
            requests.requestMatchers("/login_process").permitAll();
            requests.requestMatchers("/logout_process").permitAll();
            requests.anyRequest().authenticated();
        }).formLogin((formLogin) -> {
            formLogin.loginPage("/login")          // The custom login page
                    .usernameParameter("username") // 定义前端表单提交的参数名称
                    .passwordParameter("password") // 页面提供的<input>数据
                    .loginProcessingUrl("/login_process") // The REST API to submit username and password
                    .successHandler(new CustomAuthSuccessHandler())
                    .failureHandler(new CustomAuthFailureHandler());
        }).logout((logout) -> {
            logout.logoutUrl("/logout_process") // The Rest API to process logout
                    .invalidateHttpSession(true)  // 将Spring生成的JSESSIONID数据删除
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler());
                });
        return http.build();
    }
}
