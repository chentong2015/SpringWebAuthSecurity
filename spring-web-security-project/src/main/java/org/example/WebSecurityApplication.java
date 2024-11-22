package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSecurityApplication {

    public static void main(String[] args) {
        System.out.println("Run application");
        SpringApplication.run(WebSecurityApplication.class, args);
    }
}
