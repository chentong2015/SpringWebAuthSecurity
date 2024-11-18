package web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    @GetMapping("/")
    public String basePage() {
        return "redirect:home";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminPage() {
        return "/admin.html";
    }

    @GetMapping("/denied")
    public String deniedPage() {
        return "/denied.html";
    }

    @GetMapping("/error")
    public String customErrorController() {
        return "/error.html";
    }
}
