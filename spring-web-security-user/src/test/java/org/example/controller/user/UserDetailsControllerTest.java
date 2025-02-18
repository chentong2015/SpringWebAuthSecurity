package org.example.controller.user;

import org.example.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // hasRole("USER") 使用自定义UserDetails用户测试
    @Test
    @WithUserDetails(value = "user1")
    void testUserDetailsWithName() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User Home page"));
    }

    // 使用未定义的UserDetails用户测试, 抛出异常
    // org.springframework.security.core.userdetails.UsernameNotFoundException: unknown
    @Test
    @WithUserDetails(value = "unknown")
    void testUserDetailsUnknown() throws Exception {
         mockMvc.perform(get("/user")).andDo(print());
    }
}
