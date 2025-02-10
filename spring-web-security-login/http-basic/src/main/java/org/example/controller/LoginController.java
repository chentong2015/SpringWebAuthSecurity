package org.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(Authentication authentication) {
        System.out.println("Authenticated user: " + authentication.getName());

        return "Login success";
    }
}
