package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 只能使用Controller注解来加载View页面
@Controller
public class LoginController {

    // TODO. 使用ThymeleafView解析器，解析到静态目录src/main/resources/templates/
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
