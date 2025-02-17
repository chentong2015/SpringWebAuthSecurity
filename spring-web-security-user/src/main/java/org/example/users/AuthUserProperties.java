package org.example.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO. 属性配置文件来加载所有User配置信息
@ConfigurationProperties(prefix = "auth")
public class AuthUserProperties {

    private Map<String, UserDetail> users;

    public Map<String, UserDetail> getUsers() {
        return users;
    }

    public void setUsers(Map<String, UserDetail> users) {
        this.users = users;
    }

    public Set<UserDetails> getUserDetails() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return this.users.entrySet().stream()
                .map(entry -> User.withUsername(entry.getKey())
                        .passwordEncoder(passwordEncoder::encode)
                        .password(entry.getValue().getPassword())
                        .roles(entry.getValue().getRole().toUpperCase())
                        .build())
                .collect(Collectors.toSet());
    }

    // 对应属性配置文件中的信息，必须包含setter方法
    private static class UserDetail {

        private String role;
        private String password;

        public void setRole(String role) {
            this.role = role;
        }

        public String getRole() {
            return role;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
