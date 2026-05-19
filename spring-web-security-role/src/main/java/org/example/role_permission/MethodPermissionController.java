package org.example.role_permission;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// prePostEnabled = true：启用Pre/Post风格的注解支持:
// @PreAuthorize("...")  方法执行前基于表达式SpEL做授权判断
// @PostAuthorize("...") 方法执行后基于返回结果做授权判断
@RestController
public class MethodPermissionController {

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

    // 复合角色授权判断
    @GetMapping("/test5")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN') && hasAuthority('READ')")
    public String test5() {
        return "test 5: isAuthenticated() && hasRole('ADMIN') && hasAuthority('READ')";
    }

    // 不存在特定的能够方法API的角色
    @GetMapping("/test_none")
    @PreAuthorize("isAuthenticated() && hasRole('MASTER')")
    public String testNone() {
        return "test 5: isAuthenticated() && hasRole('MASTER')";
    }
}
