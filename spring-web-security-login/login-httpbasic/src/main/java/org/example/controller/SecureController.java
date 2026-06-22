package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/secure")
    public String secure() {
        System.out.println("Access Secure");
        return "This is secure info";
    }
}