package custom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthPasswordConfig {

    // TODO. 设置URL访问的安全权限，确保用户的认证和授权
    // - url/signup 允许访问权限
    // - url/signin 允许访问权限
    // - url/...    必须认证用户
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/signup").permitAll();
                    requests.requestMatchers("/signin").permitAll();
                    requests.anyRequest().authenticated();
                });
        return httpSecurity.build();
    }
}