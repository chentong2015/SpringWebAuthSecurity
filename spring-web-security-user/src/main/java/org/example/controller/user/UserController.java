package org.example.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO. 通过SecurityFilter配置权限认证
@RestController
public class UserController {

    @GetMapping(value = "/user")
    @PreAuthorize("isAuthenticated() && hasRole('USER')")
    public ResponseEntity<String> user(HttpServletRequest request) {
        System.out.println(request.getAuthType());
        return ResponseEntity.ok("User Home page");
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("isAuthenticated() && hasRole('ADMIN')")
    public ResponseEntity<String> admin() {
        System.out.println("admin endpoints");
        return ResponseEntity.ok("ADMIN Home page");
    }
}
