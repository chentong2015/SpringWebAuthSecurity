package org.example.controller;

import org.example.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    private static final String FLAG_CURRENT_USER = "CURRENT_USER";

    // Login时需要提供用户信息用并由auth service验证
    // 验证成功后在请求的session中添加属性用于表明身份认证成功
    @GetMapping("/login")
    public User login(HttpServletRequest request) {
        User loginUser = new User(1, "chen");
        request.getSession().setAttribute(FLAG_CURRENT_USER, loginUser);
        return loginUser;
    }

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(HttpSession httpSession) {
        System.out.println(httpSession.getId());
        return httpSession.getAttribute(FLAG_CURRENT_USER);
    }

    // 必须先删除Session中存储的用户登陆认证属性，然后再将Session失效
    @GetMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.removeAttribute(FLAG_CURRENT_USER);
        httpSession.invalidate();
        System.out.println("Session removed");
    }
}
