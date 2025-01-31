package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthSecurityConfig {

    // TODO. 使用Basic Authentication Popup表单来提交验证的账户
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/public").permitAll();
            requests.requestMatchers("/login").permitAll();
            requests.anyRequest().authenticated();
        }).formLogin((formLogin) -> {
            formLogin.loginPage("/login") // 自定义登陆页面的路径表单或使用login.html
                    .usernameParameter("username") // 定义前端表单提交的参数名称
                    .passwordParameter("password")
                    .loginProcessingUrl("/login-processing") // 自定义login的对应的表单接口
                    .successHandler(new CustomAuthSuccessHandler())
                    .defaultSuccessUrl("/home") // 登陆成功之后需要做redirecting页面跳转
                    .failureUrl("/login?error=true").permitAll();
        }).logout((logout) -> {
            logout.logoutSuccessUrl("/login?logout=true")
                    .invalidateHttpSession(true) // 将Spring生成的JSESSIONID数据删除
                    .deleteCookies("JSESSIONID").permitAll();;
        });
        return http.build();
    }

    // 配置内存存储的UserDetails数据，用于测试账号登录
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("chen")
                .password("chen")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
