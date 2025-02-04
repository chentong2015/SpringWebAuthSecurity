package org.example.controller;

import org.example.users.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthUserController {

    AuthenticationManager authenticationManager;

    public AuthUserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // TODO. 使用AuthenticationManager进行用户名和密码的验证，验证后添加到SecurityContext
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();

        AbstractAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().body("Created Success");
    }
}
