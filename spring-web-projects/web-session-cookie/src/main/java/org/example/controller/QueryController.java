package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class QueryController {

    private static final String FLAG_CURRENT_USER = "CURRENT_USER";

    @GetMapping("/query")
    public String query(HttpSession httpSession) {
        if (httpSession.getAttribute(FLAG_CURRENT_USER) == null) {
            return "please login first";
        }
        return "query data ok";
    }
}
