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

    @Test
    // @WithMockUser 无需用户认证便可以登录
    void testHomePermitAll() throws Exception {
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser // 默认角色为ROLE_USER
    void testUserAccess() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test // 没有用户，测试认证失败 => 403 禁止访问
    void testUserAccess_AuthenticationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser // 用户角色不够 => 403 禁止操作
    void testUserAccess_AuthorizationFailed() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser // 用户角色不匹配 => 403 禁止操作
    void testAdminAccess() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test // 满足特定条件的访问
    @WithMockUser(roles = {"ADMIN"})
    void testAdminAccessOK() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
