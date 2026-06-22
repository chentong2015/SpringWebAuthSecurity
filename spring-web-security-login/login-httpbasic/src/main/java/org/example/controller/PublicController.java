package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 无需登录账号也能访问的API
@RestController
public class PublicController {

    @GetMapping("/public")
    public String publicPage() {
        return "Welcome to Public Page";
    }
}
