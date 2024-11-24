package main.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthUserController {

    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> home() {

        return ResponseEntity.ok("Dashboard Home page");
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() && hasRole('USER')")
    public ResponseEntity<String> user(HttpServletRequest request) {
        System.out.println(request.getAuthType());
        return ResponseEntity.ok("User Home page");
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin() {

        return ResponseEntity.ok("ADMIN Home page");
    }
}
