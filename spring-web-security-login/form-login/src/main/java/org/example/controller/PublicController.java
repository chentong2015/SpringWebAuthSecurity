package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    // 无需登录账号也能访问的API
    @GetMapping("/public")
    public String publicPage() {
        return "Welcome to Public Page";
    }
}
