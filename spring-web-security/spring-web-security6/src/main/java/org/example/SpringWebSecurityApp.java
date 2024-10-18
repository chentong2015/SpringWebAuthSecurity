package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebSecurityApp {

    public static void main(String[] args) {
        System.out.println("Run application");
        SpringApplication.run(SpringWebSecurityApp.class, args);
    }
}
