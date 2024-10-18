package com.example.security.config;

import com.example.security.filter.CustomHeaderValidatorFilter;
import com.example.security.handler.UserAuthenticationErrorHandler;
import com.example.security.handler.UserForbiddenErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity // 注入HttpSecurity Bean
@EnableConfigurationProperties(AuthUserProperties.class)
public class SecurityConfiguration {

    private final AuthUserProperties props;

    public SecurityConfiguration(AuthUserProperties props) {
        this.props = props;
    }

    // TODO. Spring Security 核心三大组件之一：Servlet Filters 过滤器
    @Bean
    @Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 在执行Filter Chain之前执行自定义的Filter
        http.addFilterBefore(new CustomHeaderValidatorFilter(), BasicAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/library/**").permitAll()
                .antMatchers(HttpMethod.GET, "/library/**").hasRole("USER")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new UserAuthenticationErrorHandler())
                        .accessDeniedHandler(new UserForbiddenErrorHandler()));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(props.getUserDetails());
    }

    // 认证时使用的密码加密方法需要和用户属性加载时所使用的Encoder一致
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(userDetailsService())
                        .passwordEncoder(new BCryptPasswordEncoder());
        return builder.and().build();
    }

    // 使用用户名和密码来做Authentication认证的过滤
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(HttpSecurity http) throws Exception {
        return new UsernamePasswordAuthenticationFilter(authenticationManager(http));
    }
}
