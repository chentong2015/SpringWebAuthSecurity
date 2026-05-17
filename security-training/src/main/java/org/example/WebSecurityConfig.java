package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests((requests) -> {
            // Index page 登录页面公开访问
            requests.requestMatchers("/").permitAll();

            requests.requestMatchers("/view").authenticated();
            requests.requestMatchers("/profile").authenticated();
            requests.requestMatchers("/admin").hasRole("ADMIN");

            // 不要使用模糊路径标识，可能造成Path路径失去安全控制
            requests.requestMatchers("/**").hasRole("ADMIN");

            // 应该限制Swagger Page的访问，避免敏感的API Endpoints泄露
            requests.requestMatchers("/swagger-ui/**",
                    "/v2/api-docs/**",
                    "/swagger-resources/**").hasRole("ADMIN");

            // 始终控制任何路径的安全授权
            requests.anyRequest().authenticated();
        });
        httpSecurity.formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password(passwordEncoder().encode("user1"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
