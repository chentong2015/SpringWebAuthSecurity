package controller;

import model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthSessionController {

    @Autowired
    AuthenticationManager authenticationManager;

    // TODO. 登录时验证用户信息 + 生成Token + 创建用户Session
    @PostMapping("/signin")
    public ResponseEntity<?> authUserSession(@RequestBody UserRequest userRequest) {
        String username = userRequest.getUsername();

        // 通过认证器Manager完成基于User+Password的认证，完成后共享到SecurityContext
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JwtTokenProvider tokenProvider = new JwtTokenProvider();
        // String token = tokenProvider.generateJwtToken(username);
        // return ResponseEntity.ok(new JwtResponse(token, username));
        return ResponseEntity.ok().body("test");
    }
}
