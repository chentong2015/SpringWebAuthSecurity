package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// TODO. ThymeleafView 页面解析器
// - 必须使用Controller注解来加载View页面
// - 解析静态目录src/main/resources/templates/中的页面
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        // 使用自定义的登录页面
        return "login";
    }
}
