package main.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties({AuthUserProperties.class})
public class AuthSecurityUserConfig {

    @Autowired
    private AuthUserProperties props;

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(HttpSecurity http) throws Exception {
        return new UsernamePasswordAuthenticationFilter(authenticationManager(http));
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService());
        return authenticationManagerBuilder.build();
    }

    // TODO. 静态注入: 通过properties属性配置文件注入用户信息
    @Bean
    public UserDetailsService userDetailsService() {
        // 直接静态注入用户的信息
        // PasswordEncoder encoder = new BCryptPasswordEncoder();
        // UserDetails user = User.withUsername("User1")
        //         .password(encoder.encode("password1"))
        //         .roles("USER")
        //         .build();

        return new InMemoryUserDetailsManager(props.getUserDetails());
    }
}
