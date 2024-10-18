package com.example.https.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/")
    public String getGreeting() {
        return "Welcome to Spring Boot on HTTPS!";
    }
}
