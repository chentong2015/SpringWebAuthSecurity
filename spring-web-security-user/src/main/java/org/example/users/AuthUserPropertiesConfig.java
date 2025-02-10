package org.example.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableConfigurationProperties({AuthUserProperties.class})
public class AuthUserPropertiesConfig {

    @Autowired
    private AuthUserProperties props;

    // TODO. 静态注入属性配置文件中的用户信息，初始化认证用户
    // @Bean
    public UserDetailsService userDetailsServiceStatic() {
        return new InMemoryUserDetailsManager(props.getUserDetails());
    }
}
