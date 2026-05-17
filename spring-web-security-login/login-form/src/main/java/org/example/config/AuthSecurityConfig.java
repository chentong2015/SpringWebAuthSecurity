package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
public class AuthSecurityConfig {

    // TODO. 定义Login成功或失败后自动跳转页面url链接
    // @Bean
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
            formLogin.loginPage("/login")          // 自定义login.html页面
                    .usernameParameter("username") // 定义前端表单提交的参数名称
                    .passwordParameter("password") // 页面提供的<input>数据
                    .loginProcessingUrl("/login_process") // The REST API to submit 接受请求的用户名和密码
                    .defaultSuccessUrl("/home")    // The landing page after a successful login
                    .failureUrl("/login?error=true");
        }).logout((logout) -> {
            logout.logoutUrl("/logout_process")    // The Rest API to process logout 用于接受登出请求
                    // .logoutRequestMatcher(new AntPathRequestMatcher("/logout_process"))
                    .logoutSuccessUrl("/login?logout=true") // 登出之后返回到Login登录页面
                    .invalidateHttpSession(true)   // 将Spring生成的JSESSIONID数据删除
                    .deleteCookies("JSESSIONID");
                });
        return http.build();
    }
}
