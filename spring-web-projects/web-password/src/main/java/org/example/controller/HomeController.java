package org.example.controller;

import org.example.credentials.UserCredentials;
import org.example.service.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 注册的Endpoint是所有访问被允许: 在注册同时对Password进行加密
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserCredentials userCredentials) {
        String username = userCredentials.getUsername();
        String encodedPassword = passwordEncoder.encode(userCredentials.getPassword());
        UserCredentials user = new UserCredentials(username, encodedPassword);
        userRepository.save(user);
    }

    // 访问其它的Endpoints是需要提供身份信息用于认证的
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
