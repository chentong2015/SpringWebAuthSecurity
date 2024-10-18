package com.security.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// TODO. 只能使用Controller注解来加载View页面
@Controller
public class LoginController {

    // TODO. 使用ThymeleafView解析器，默认解析到项目的静态目录
    //  Default ThymeleafViewResolver that has the default template directory as
    //  src/main/resources/templates/login.html
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
