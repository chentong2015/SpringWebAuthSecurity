package com.oauth2.main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    // 提供可以自由访问的Endpoints
    @GetMapping("/index")
    public String index() {
        return "hello index";
    }

    // 提供需要Login登录之后才能访问的Endpoints
    @GetMapping("/secure")
    public String secure() {
        return "hello secure";
    }
}
