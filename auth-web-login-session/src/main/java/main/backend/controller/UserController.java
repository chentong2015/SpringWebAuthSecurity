package main.backend.controller;

import main.backend.model.entity.UserEntity;
import main.backend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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


    // 从Context中获取认证过的UserDetails
    // Make sure user has role "ROLE_USER" to access this endpoint.
    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public UserEntity user() {
        return (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    // findUserByUsername()会确保用户具体指定的Role
    @GetMapping("/showme/{username}")
    public UserEntity showMe(@PathVariable String username) {
        return this.userService.findUserByUsername(username);
    }
}
