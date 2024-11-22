package main;

import main.properties.AuthUserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AuthSecurityUserConfig {

    @Autowired
    private AuthUserProperties props;

    // TODO. 用户Details信息内存存储器，数据并没有持久化
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     PasswordEncoder encoder = new BCryptPasswordEncoder();
    //     UserDetails user = User.withUsername("User1")
    //             .password(encoder.encode("password1"))
    //             .roles("USER")
    //             .build();
    //     return new InMemoryUserDetailsManager(user);
    // }

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
