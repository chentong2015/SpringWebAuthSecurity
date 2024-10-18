package org.example.credentials;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// 存储到数据库中的用户身份认证表
@Entity
@Table(name = "users")
public class UserCredentials {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    // 为每个用户设置不同的角色
    // private Set<String> roles;

    public UserCredentials() {
    }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
