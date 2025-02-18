package org.example.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping(value = "/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Dashboard Home page");
    }
}
