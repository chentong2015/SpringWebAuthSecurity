package org.example.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO. 使用注解的方式配置认证和权限
@RestController
public class PreAuthorizeController {

    @GetMapping("/test1")
    @PreAuthorize("permitAll()")
    public String test1() {
        return "test 1: permitAll()";
    }

    @GetMapping("/test2")
    @PreAuthorize("isAuthenticated()")
    public String test2() {
        return "test 2: isAuthenticated()";
    }

    @GetMapping("/test3")
    @PreAuthorize("isAuthenticated() && hasRole('USER')")
    public String test3() {
        return "test 2: isAuthenticated() && hasRole('USER')";
    }

    @GetMapping("/test4")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    public String test4() {
        return "test 4: isAuthenticated() && hasRole('ADMIN')";
    }

    // 不存在特定的能够方法API的角色
    @GetMapping("/test5")
    @PreAuthorize("isAuthenticated() && hasRole('MASTER')")
    public String test5() {
        return "test 5: isAuthenticated() && hasRole('MASTER')";
    }
}
