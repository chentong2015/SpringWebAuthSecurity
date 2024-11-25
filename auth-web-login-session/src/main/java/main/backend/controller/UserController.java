package main.backend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    // Reset Password 重置当前用户的密码

    // Update Password
    // @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    // @PreAuthorize("hasRole('USER')")
    // public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) throws Exception {
    //     userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
    //     Map<String, String> result = new HashMap<>();
    //     result.put("result", "success");
    //     return ResponseEntity.accepted().body(result);
    // }

    // static class PasswordChanger {
    //     public String oldPassword;
    //     public String newPassword;
    // }


    // Show me 显示当前的用户
    // Find my user name
}
