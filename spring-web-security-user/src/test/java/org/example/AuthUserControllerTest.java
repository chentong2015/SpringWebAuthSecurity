package org.example;

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

@SpringBootTest
@AutoConfigureMockMvc
public class AuthUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 无需用户认证便可以登录
    @Test
    // @WithMockUser
    void testHomePermitAll() throws Exception {
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 默认角色为ROLE_USER
    @Test
    @WithMockUser(roles = {"USER"})
    void testUserAccess() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 用户角色不匹配 => 403 禁止操作
    @Test
    @WithMockUser
    void testAdminAccess() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // 满足特定条件的访问
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAdminAccessOK() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 没有用户，测试认证失败 => 403 禁止访问
    @Test
    void testUserAccess_AuthenticationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // 用户角色不够 => 403 禁止操作
    @Test
    @WithAnonymousUser
    void testUserAccess_AuthorizationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


}
