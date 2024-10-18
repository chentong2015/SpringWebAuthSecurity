package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/health")
    public String health() {
        System.out.println("call health method");
        return "Call Health";
    }

    @GetMapping("/token/get-token")
    public String token() {
        System.out.println("check token");
        return "Check token";
    }

    @GetMapping("/home")
    public String home() {
        System.out.println("Home page");
        return "Home";
    }
}
