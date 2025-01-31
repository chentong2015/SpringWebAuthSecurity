package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // 无需登录账号也能访问的API
    @GetMapping("/public")
    public String publicPage() {
        return "Welcome to Public Page";
    }

    // TODO. 当User通过表单的账号验证后才能访问的API
    @GetMapping("/")
    public String index() {
        return "redirect:secure";
    }

    @GetMapping("/secure")
    public String secure() {
        return "Welcome to Secure Page";
    }
}
