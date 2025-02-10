package org.example.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

// TODO. 定义AuthenticationManager修改UserDetails密码的实现方式
@Service
public class DBUserDetailsPasswordService implements UserDetailsPasswordService {

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // Check User exist and update its password
        return user;
    }
}
