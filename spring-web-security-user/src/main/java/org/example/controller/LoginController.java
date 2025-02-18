package org.example.controller;

import org.example.model.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // TODO. 创建用于AuthenticationManager认证的Authentication对象(认证通过与否)
    @PostMapping("/login")
    @PreAuthorize("permitAll()") // Endpoint可以自由访问, 任意用户都能被认证成功
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest);
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();

        // ROLE Authority授权失败, 测试角色授权失败
        AbstractAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body("Created Success");
    }
}
