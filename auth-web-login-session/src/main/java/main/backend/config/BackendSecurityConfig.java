package main.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BackendSecurityConfig {

    // 针对密码的高级加密器, 提供SALT避免被破解
    @Bean
    public PasswordEncoder globalPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
