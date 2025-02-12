package org.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO. 使用注解的方式配置认证和权限
@RestController
public class SecurityController {

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
        return "test 2: isAuthenticated()";
    }

    @GetMapping("/test4")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    public String test4() {
        return "test 4: isAuthenticated()";
    }
}
