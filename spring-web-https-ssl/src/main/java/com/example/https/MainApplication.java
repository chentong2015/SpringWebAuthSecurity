package com.example.https;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    // 启动应用在指定的443端口，HTTPS默认端口号
    // ...
    // Tomcat started on port(s): 443 (https) with context path ''
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}