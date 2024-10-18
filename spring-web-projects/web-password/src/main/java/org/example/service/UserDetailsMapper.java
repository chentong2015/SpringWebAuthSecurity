package org.example.service;

import org.example.credentials.UserCredentials;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// 提供从自定义UserCredentials对象到Spring UserDetails的映射
@Component
public class UserDetailsMapper {

    public UserDetails toUserDetails(UserCredentials userCredentials) {
        return User.withUsername(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles("User")
                .build();
    }
}
