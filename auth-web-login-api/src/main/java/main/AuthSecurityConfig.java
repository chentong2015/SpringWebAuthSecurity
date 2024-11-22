package main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)
public class AuthSecurityConfig {

    // TODO. 设置URL访问的安全权限，确保用户的认证和授权
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/home").permitAll();
                    requests.anyRequest().authenticated();
                });
        return httpSecurity.build();
    }

    // TODO. 用户Details信息内存存储器，数据并没有持久化
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        UserDetails user = User.withUsername("User1")
                .password(encoder.encode("password1"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("User2")
                .password(encoder.encode("password2"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
