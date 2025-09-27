package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // 使用自定义的登录页面, 返回自定义的Html页面
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
