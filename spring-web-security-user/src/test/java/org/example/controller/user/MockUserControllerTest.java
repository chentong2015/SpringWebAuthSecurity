package org.example.controller.user;

import org.example.SpringWebSecurityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SpringWebSecurityApplication.class)
@AutoConfigureMockMvc
public class MockUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // hasRole("USER") => 401 UNAUTHORIZED 没有提供任何认证用户
    @Test
    void testUserAccess_AuthenticationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    // hasRole("USER") => 401 UNAUTHORIZED 认证失败的无效用户
    @Test
    @WithAnonymousUser
    void testUserAccess_AuthorizationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    // hasRole("USER") 默认角色为ROLE_USER
    @Test
    @WithMockUser
    void testMockUser() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // hasRole("USER") 设置授权角色
    @Test
    @WithMockUser(roles = {"USER"})
    void testMockUserWithAuthorities() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // hasRole("USER") 使用自定义的UserDetails用户信息
    @Test
    @WithMockUser(username = "user1", password = "user1")
    void testMockUserWithUsername() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 创建新的测试角色用户
    // org.springframework.security.core.userdetails.User
    // [Username=user2, Password=[PROTECTED], Granted Authorities=[ROLE_USER]], Authenticated=true]]
    @Test
    @WithMockUser(username = "user2", password = "user2")
    void testMockUserWithNewUsername() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 创建Role错误的新角色
    // org.springframework.security.core.userdetails.User
    // [Username=user2, Password=[PROTECTED], Granted Authorities=[TEST]], Authenticated=true]]
    @Test
    @WithMockUser(username = "user3", password = "user3", roles = "UNKNOWN") // authorities = "TEST"
    void testMockUserWithWrongRole() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
