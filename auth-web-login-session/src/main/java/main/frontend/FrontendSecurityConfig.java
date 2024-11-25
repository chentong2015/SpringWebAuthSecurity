package main.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class FrontendSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/login").permitAll();
                    requests.requestMatchers("/register").permitAll();
                    requests.requestMatchers("/public").permitAll();
                    requests.requestMatchers("/user").authenticated();
                    requests.requestMatchers("/admin").authenticated();
                    requests.requestMatchers("/public-secure").authenticated();
                });
        return httpSecurity.build();
    }
}
