package com.example.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

// @PreAuthorize: Authorizes the condition before executing the method.
// @PostAuthorize: Authorizes the condition after the method is executed
//  @EnableGlobalMethodSecurity(prePostEnabled = true) to our configuration class
@Controller
public class SecurityController {

    // 1. 测试Servlet Filter Before
    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "Login success";
    }

    // 2. 测试Authentication Filter: 必须指定的用户的访问
    @GetMapping("/library/strings")
    public ResponseEntity<String> getString(@RequestParam String genre) {
        return ResponseEntity.ok().body("get string");
    }

    // 2. 测试Authentication Filter: 必须指定的用户的访问
    @GetMapping("/library/books")
    @PreAuthorize("#user == authentication.principal.username")
    public ResponseEntity<String> getBooks(@RequestParam String genre, @RequestParam String user) {
        return ResponseEntity.ok().body(genre + "books");
    }

    // 3. 测试Authorization Filter: 只有指定角色的用户可以访问
    @GetMapping("/library/books/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> getAllBooks() {
        return ResponseEntity.ok().body("all books");
    }

    // 3. 测试Authorization Filter: 只有授权了指定操作的用户可以访问
    @GetMapping("/library/books/content")
    @PreAuthorize("hasAuthority('generator::read')")
    public ResponseEntity<String> getBooksContent() {
        return ResponseEntity.ok().body("Books contents");
    }

    // 4. 测试Unsecure Specific Endpoints
    @GetMapping("/library/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.ok().body("Info book");
    }

    // 5. 测试Session Management
    @GetMapping("/index")
    public String homePage(HttpServletResponse response) {
        return "index page";
    }

    @GetMapping("/invalidSession")
    public String invalidSession(HttpServletResponse response) {
        return "invalidSession";
    }
}
