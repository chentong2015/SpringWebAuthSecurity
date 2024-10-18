package com.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 使用Mock User来进行测试
    // @WithMockUser(username = "user1", password = "test1", roles = {"user"})

    // 使用自定义的User Details来测试Endpoints
    @Test
    @WithUserDetails(value = "user1", userDetailsServiceBeanName = "userDetailsService")
    void successIfSecurityApplies() throws Exception {
        mockMvc.perform(get("/library/strings")
                        .param("genre", "Title")
                        .header("X-Application-Name", "Library"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated().withUsername("user1"))
                .andExpect(authenticated().withRoles("USER"));
    }

    // 1. 没有提供指定的Header信息"X-Application-Name"，会被Filter过滤并抛出403 Forbidden异常
    @Test
    @WithUserDetails(value = "user1", userDetailsServiceBeanName = "userDetailsService")
    void failsIfSecurityApplyFilter() throws Exception {
        mockMvc.perform(get("/library/strings")
                        .param("genre", "Title"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // 2. 当没有提供任何的User信息时，会抛出401 Unauthorized错误
    @Test
    void failsIfSecurityApplies() throws Exception {
        mockMvc.perform(get("/library/strings")
                        .param("genre", "Title")
                        .header("X-Application-Name", "Library"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    // 3. 当提供的User不具备指定的Role角色时，会抛出403 Forbidden异常
    @Test
    @WithMockUser(username = "user1", roles = {"DEMO"})
    void failsForWrongAuthorization() throws Exception {
        mockMvc.perform(get("/library/strings")
                        .param("genre", "Title")
                        .header("X-Application-Name", "Library"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
